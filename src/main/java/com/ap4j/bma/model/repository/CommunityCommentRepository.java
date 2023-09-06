package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.community.CommunityCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityCommentRepository extends JpaRepository<CommunityCommentEntity, Integer>{

//    @Query("SELECT c FROM CommunityCommentEntity c join  CommunityEntity e ON e.id = ?1 order by c.createdAt asc")
    @Query("SELECT c FROM CommunityCommentEntity c JOIN c.communityEntity e WHERE e.id = ?1 ORDER BY c.createdAt ASC")

    public List<CommunityCommentEntity> findAllComment(Integer id);
}
