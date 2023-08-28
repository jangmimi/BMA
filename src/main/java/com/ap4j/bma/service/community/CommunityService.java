package com.ap4j.bma.service.community;

import com.ap4j.bma.model.entity.community.CommunityEntity;
import com.ap4j.bma.model.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    //글작성처리
    public CommunityEntity saveCommunity(CommunityEntity communityEntity) {
        return communityRepository.save(communityEntity);
    }

    //게시글 리스트 처리
    public List<CommunityEntity> getAllCommunity() {
        return communityRepository.findAll();

    }

    //상세페이지 보기
    public CommunityEntity communityView(Integer id) {
        return communityRepository.findById(id).get();
    }


    public void communityDelete(Integer id) {

        communityRepository.deleteById(id);
    }
}
