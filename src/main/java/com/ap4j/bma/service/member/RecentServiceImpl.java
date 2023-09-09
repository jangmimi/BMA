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
import org.springframework.stereotype.Service;

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
}
