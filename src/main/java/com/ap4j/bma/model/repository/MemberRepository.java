package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

// DB와 직접 소통하는 인터페이스. JPA가 해당 객체를 알아서(자동) 생성
// JPA 전용 인터페이스
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {   // ..JpaRepository<관리 대상, 대상의 PK 타입>

    MemberEntity findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByName(String name);

}
