package com.ap4j.bma.service.member;

import com.ap4j.bma.model.entity.member.LikedEntity;
import com.ap4j.bma.model.repository.LikedRepository;
import com.ap4j.bma.model.repository.MaemulRegRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

//    public void addLike(Member member, Long postId, String postEmail, String postTitle) {
//        LikedEntity like = new LikedEntity();
//        like.setMember(member);
//        like.setPostId(postId);
//        like.setPostEmail(postEmail);
//        like.setPostTitle(postTitle);
//        likeRepository.save(like);
//    }

}
