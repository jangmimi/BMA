package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// DB와 직접 소통하는 인터페이스. JPA가 해당 객체를 알아서(자동) 생성
// JPA 전용 인터페이스
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {   // ..JpaRepository<관리 대상, 대상의 PK 타입>

    @Override
    Optional<MemberEntity> findById(Long id);   // id로 회원 정보 조회

    // email, nickname 중복 체크
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    Optional<MemberEntity> findByEmail(String email);   // email로 회원 정보 조회
    Optional<MemberEntity> findByNameAndTel(String name, String tel);   // email 찾기

}
