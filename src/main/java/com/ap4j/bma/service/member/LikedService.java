package com.ap4j.bma.service.member;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.entity.member.LikedEntity;
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.repository.LikedRepository;
import com.ap4j.bma.model.repository.MaemulRegEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LikedService {
    @Autowired
    private LikedRepository likedRepository;

    @Autowired
    private MaemulRegEntityRepository maemulRegEntityRepository;

    public Long countAll() {
        return likedRepository.count();
    }

    public List<LikedEntity> getAllList() {
        return likedRepository.findAll();
    }

    /** 특정 닉네임이랑 매치되는 Liked 전부 조회 */
    public List<LikedEntity> findLikedByNickname(String nickname) {
        return likedRepository.findLikedByNickname(nickname);
    }

    /** 로그인한 멤버가 관심등록한 매물만 조회 */
    public List<MaemulRegEntity> filterMaemulListByNickname(String nickname, List<LikedEntity> likedList, List<MaemulRegEntity> maemulList) {
        return likedList.stream()
                .filter(likedEntity -> likedEntity.getNickname().equals(nickname))
                .flatMap(likedEntity -> maemulList.stream()
                        .filter(maemulRegEntity -> likedEntity.getMaemul_id().equals(maemulRegEntity.getId())))
                .collect(Collectors.toList());
    }
    /** 로그인한 멤버의 관심매물 조회 */
    public List<MaemulRegEntity> myLikedList(String nickname) {
        return likedRepository.findMaemulByUserNickname(nickname);
    }


    /** 관심 매물 저장 (*중복 저장 안되게 작업중) */
    @Transactional
    public Long save(LikedEntity likeEntity) {
        String nickname = likeEntity.getNickname();
        Integer maemul_id = likeEntity.getMaemul_id();
        boolean isDuplicate = likedRepository.existsByNicknameAndMaemulId(nickname, maemul_id);

        if(!isDuplicate) {
            likedRepository.save(likeEntity); log.info("중복아니라 저장완료");
        } else {
            log.info("중복된 매물이네요. 삭제할게요.");
            deleteByMaemulIdAndNickname(maemul_id, nickname);
        }
        return likeEntity.getId();
    }

    /** 관심매물 삭제 */
    @Transactional
    public void deleteByMaemulIdAndNickname(Integer maemul_id, String nickname) {
        log.info("서비스에서 삭제 시행");
        likedRepository.deleteByMaemulIdAndNickname(maemul_id,nickname);
    }
    /*김재환작성 페이징처리*/
    public Page<MaemulRegEntity> getPaginatedItems(String nickname, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<MaemulRegEntity> mmpList = maemulRegEntityRepository.findLikedByNicknameAndPaging(nickname,pageable);
        System.out.println(mmpList);
        return mmpList;
    }

    /*김재환작성 관심매물 전체개수*/
    public Long countLikedByNickname(String nickname) {
        return likedRepository.countLikedByNickname(nickname);
    }

//    @Transactional
//    public void delete(LikedEntity likedEntity) {
//        log.info("LikedService 관심매물 삭제 실행");
//        likedRepository.delete(likedEntity);
//    }

//    public Optional<LikedEntity> findByNicknameAndRoad_name(String nickname, String road_name) {
//        return likedRepository.findByNicknameAndRoad_name(nickname, road_name);
//    }

}
