package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.community.CommunityCommentEntity;
import com.ap4j.bma.model.entity.community.CommunityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityCommentRepository extends JpaRepository<CommunityCommentEntity, Integer>{

}
