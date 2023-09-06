package com.ap4j.bma.service.member;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.entity.member.LikedEntity;
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.repository.LikedRepository;
import com.ap4j.bma.model.repository.MaemulRegRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LikedService {
    @Autowired
    private LikedRepository likedRepository;

    @Autowired
    private MaemulRegRepository maemulRegRepository;

    public Long countAll() {
        return likedRepository.count();
    }

    public List<LikedEntity> getAllList() {
        return likedRepository.findAll();
    }

    public List<LikedEntity> findLikedByNickname(String nickname) {
        return likedRepository.findLikedByNickname(nickname);
    }

    public List<MaemulRegEntity> findLikedByRoadname(String road_name) {
        return likedRepository.findMaemulByRoadName(road_name);
    }
    
//    public List<MaemulRegEntity> findLikedByRoadname(String roadName) {
//        List<MaemulRegEntity> likedmList = likedRepository.findMaemulByRoadName(roadName);
//
//        return likedRepository.findMaemulByRoadName(maemulList).stream()
//                .map(LikedEntity::getMaemul)
//                .collect(Collectors.toList());
//    }

//    public List<MaemulRegEntity> findMaemulByRoadNames(List<String> roadNames) {
//        List<LikedEntity> likedEntities = likedRepository.findByMaemulRoadNameIn(roadNames);
//
//        // LikedEntity에서 MaemulRegEntity로 변환
//        List<MaemulRegEntity> maemulRegEntities = likedEntities.stream()
//                .map(LikedEntity::getMaemul)
//                .collect(Collectors.toList());
//
//        return maemulRegEntities;
//    }
    /** 관심 매물 저장 */
    public Long save(LikedEntity likeEntity) {
        likedRepository.save(likeEntity);
        return likeEntity.getId();
    }
}
