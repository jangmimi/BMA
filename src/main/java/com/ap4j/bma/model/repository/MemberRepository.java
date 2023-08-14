package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// JPA 전용 인터페이스
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
}
