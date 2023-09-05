package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.member.LikedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikedRepository extends JpaRepository<LikedEntity, Long> {
}
