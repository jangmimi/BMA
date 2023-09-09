package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.entity.member.MemberEntity;
import com.ap4j.bma.model.entity.recentView.RecentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentRepository extends JpaRepository<RecentEntity, Integer> {
//    @Query("SELECT mr FROM RecentEntity r JOIN MaemulRegEntity mr ON r.MAEMUL_ID = mr.id WHERE r.nickname = :recentEntity")
//    Page<MaemulRegEntity> findRecentMaemul(RecentEntity recentEntity, Pageable pageable);



}
