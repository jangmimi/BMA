package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// DB와 직접 소통하는 인터페이스. JPA가 해당 객체를 알아서(자동) 생성
// JPA 전용 인터페이스
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {   // ..JpaRepository<관리 대상, 대상의 PK 타입>

    Optional<MemberEntity> findByEmail(String email);   // email로 회원 정보 조회
    @Override
    Optional<MemberEntity> findById(Long id);          // id로 회원 정보 조회

    boolean existsByEmail(String email);    // exists : 해당 데이터가 DB에 존재하는지 확인하기 위해 사용

    Optional<MemberEntity> findByNameAndTel(String name, String tel);   // email 찾기

    Optional<MemberEntity> findByEmailAndTel(String email, String tel);   // pwd 찾기

}
