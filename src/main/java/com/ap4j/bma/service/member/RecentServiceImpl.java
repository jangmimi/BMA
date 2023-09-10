package com.ap4j.bma.service.member;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.entity.member.MemberEntity;
import com.ap4j.bma.model.entity.recent.RecentDTO;
import com.ap4j.bma.model.entity.recent.RecentEntity;
import com.ap4j.bma.model.repository.MaemulRegEntityRepository;
import com.ap4j.bma.model.repository.MemberRepository;
import com.ap4j.bma.model.repository.RecentRepository;
import com.ap4j.bma.service.maemulReg.MaemulRegService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class RecentServiceImpl implements RecentService{

    private final MaemulRegEntityRepository maemulRegRepository;
    private final RecentRepository recentRepository;
    private final MemberRepository memberRepository;

    @Autowired
    public RecentServiceImpl(MaemulRegEntityRepository maemulRegRepository, RecentRepository recentRepository, MemberRepository memberRepository) {
        this.maemulRegRepository = maemulRegRepository;
        this.recentRepository = recentRepository;
        this.memberRepository = memberRepository;
    }

    public Long countAll() {
        return recentRepository.count();
    }

    public List<RecentEntity> getAllList() {
        return recentRepository.findAll();
    }


    @Override
    public void recentCheck(int id, String nickname) {
        // 클릭한 매물과 유저를 데이터베이스에서 가져옴
        MaemulRegEntity maemul = maemulRegRepository.findById(id).orElse(null);
        MemberEntity member = memberRepository.findByNickname(nickname).orElse(null);

        if (maemul != null && nickname != null || nickname.equals(null)) {
            boolean isDuplicate = recentRepository.existsByMaemulEntityAndMemberEntity_Nickname(maemul, nickname);

            if (!isDuplicate) {
                // Recent 엔터티 생성 및 저장
                RecentEntity recentEntity = new RecentEntity();
                recentEntity.setMaemulEntity(maemul);
                recentEntity.setMemberEntity(member);
                recentRepository.save(recentEntity);
            }
        }
    }

    /*페이징처리가 된 최근본매물리스트*/
    public Page<MaemulRegEntity> recentMaemulList(String nickname,int page,int pageSize){
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<MaemulRegEntity> mmpList = recentRepository.findMaemulEntitiesByMemberNickname(nickname,pageable);
        return mmpList;
    }

    /*최근본매물리스트 카운트*/
    public Long recentMamulListCount(String nickname) {
        return recentRepository.countByMemberEntity_Nickname(nickname);
    }

    /*페이징처리가 되고, 검색한 결과의 최근본매물리스트*/
    public Page<MaemulRegEntity> searchRecentMaemulList(String nickname,String keyword,int page,int pageSize) {
        Pageable pageable = PageRequest.of(page - 1,pageSize);
        Page<MaemulRegEntity> mmpList = recentRepository.findMaemulEntitiesByMemberNicknameAndKeyword(nickname,keyword,pageable);
        return mmpList;
    }

    public Long searchRecentMaemulListCount(String nickname,String keyword){
        return recentRepository.countByMemberEntity_NicknameAndMaemulEntity_AddressContainingOrMaemulEntity_TradeTypeContaining(nickname,keyword,keyword);
    }

    /** 최근매물 삭제 */
    @Transactional
    @Override
    public int recentDelete(Integer id, String nickname) {
        return recentRepository.deleteByMaemulIdAndNickname(id, nickname);
    }
}
