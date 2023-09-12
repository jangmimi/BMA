package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    /** id로 회원 정보 조회 */
    @Override
    Optional<MemberEntity> findById(Long id);

    /** email, nickname 중복 체크 */
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    /** email로 회원 정보 조회 */
    Optional<MemberEntity> findByEmail(String email);

    /** 닉네임으로 회원 정보 조회 */
    Optional<MemberEntity> findByNickname(String nickname);


	Optional<MemberEntity> findMemberByNickname(String nickname);
    /** 이름과 연락처 둘 다 일치할 경우에 정보 조회 */
    @Query("SELECT m FROM MemberEntity m WHERE m.name = :name AND m.tel = :tel")
    Optional<MemberEntity> findByNameAndTel(@Param("name") String name, String tel);
}
