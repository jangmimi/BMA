package com.ap4j.bma.service.community;

import com.ap4j.bma.model.entity.community.CommunityEntity;
import com.ap4j.bma.model.repository.CommunityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CommunityService {
    @Autowired
    private CommunityRepository communityRepository;

    //글작성처리
    public CommunityEntity saveCommunity(CommunityEntity communityEntity) {
        CommunityEntity savedEntity = communityRepository.save(communityEntity);
        updateTotalCommunityCount(); // 총 게시글 개수 업데이트
        return savedEntity;
    }

    public Long updateTotalCommunityCount() {
        Long totalCount = communityRepository.count(); // 총 게시글 개수 조회
        // totalCount를 원하는 변수에 저장하거나 활용

        return totalCount;
    }

    //게시글 리스트 처리
//    public Page<CommunityEntity> getAllCommunity(int pageNum, int pageSize) {
//        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
//        return communityRepository.findAll(pageable);
//    }

    //게시글 페이징
    public Page<CommunityEntity> getCommunityPage(Pageable pageable) {
        return communityRepository.findAll(pageable);
    }

    //상세페이지 보기
    public CommunityEntity communityView(Integer id) {
        return communityRepository.findById(id).get();
    }

    //이전글
    public Object getPreArticle(Integer id) {
        return communityRepository.findTopByIdLessThanOrderByIdDesc(id);
    }
    //다음글
    public Object getNextArticle(Integer id) {
        return communityRepository.findTopByIdGreaterThanOrderByIdAsc(id);
    }
    public void communityDelete(Integer id) {
        communityRepository.deleteById(id);
        updateTotalCommunityCount(); //총 게시글 개수 업데이트
    }

    //검색 기능
    // 제목으로 검색
    public Page<CommunityEntity> searchByTitle(String keyword, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, Sort.by("id").descending());
        return communityRepository.findByTitleContaining(keyword, pageable);
    }

}
