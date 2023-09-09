package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    @Override
    Optional<MemberEntity> findById(Long id);   // id로 회원 정보 조회

    // email, nickname 중복 체크
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    Optional<MemberEntity> findByEmail(String email);   // email로 회원 정보 조회
    Optional<MemberEntity> findByNameAndTel(String name, String tel);   // email 찾기

    Optional<MemberEntity> findByNickname(String nickname); // 닉네임 찾기


}
