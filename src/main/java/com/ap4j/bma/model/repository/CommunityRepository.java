package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.community.CommunityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<CommunityEntity, Integer> {

    // 모든필드
    Page<CommunityEntity> findByTitleContainingOrContentContainingOrAuthorContaining(String titleKeyword, String contentKeyword, String authorKeyword, Pageable pageable);
    //제목 검색
    Page<CommunityEntity> findByTitleContaining(String keyword, Pageable pageable);
    //내용 검색
    Page<CommunityEntity> findByContentContaining(String keyword, Pageable pageable);
    //작성자 검색
    Page<CommunityEntity> findByAuthorContaining(String keyword, Pageable pageable);

    CommunityEntity findTopByIdLessThanOrderByIdDesc(Integer id);

    CommunityEntity findTopByIdGreaterThanOrderByIdAsc(Integer id);

}
