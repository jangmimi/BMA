package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.member.RecentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentRepository extends JpaRepository<RecentEntity, String> {
}
