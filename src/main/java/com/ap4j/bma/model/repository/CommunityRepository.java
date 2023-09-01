package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.community.CommunityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository extends JpaRepository<CommunityEntity, Integer> {

    //검색 기능
    Page<CommunityEntity> findByTitleContaining(String keyword, Pageable pageable);

    CommunityEntity findTopByIdLessThanOrderByIdDesc(Integer id);

    CommunityEntity findTopByIdGreaterThanOrderByIdAsc(Integer id);

//    //조회수
//    @Modifying
//    @Query("update Board b set b.count = b.count + 1 where b.view = :view")
//    void updateCount(Long view);
}
