package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.entity.member.LikedEntity;
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.entity.member.MemberEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikedRepository extends JpaRepository<LikedEntity, Long> {

    @Query("SELECT l FROM LikedEntity l " +
            "JOIN MaemulRegEntity mr ON l.nickname = mr.nickname " +
            "JOIN MemberEntity m ON l.nickname = m.nickname " +
            "WHERE l.nickname = :nickname")
    List<LikedEntity> findLikedByNickname(@Param("nickname") String nickname);

    @Query("SELECT mr FROM MaemulRegEntity mr " +
            "JOIN LikedEntity l ON mr.nickname = l.nickname " +
            "JOIN MemberEntity m ON mr.nickname = m.nickname " +
            "WHERE l.road_name = :roadName")
    List<MaemulRegEntity> findMaemulByRoadName(@Param("roadName") String roadName);

    // 사용자 닉네임과 도로 주소를 이용한 중복 체크 쿼리
    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END " +
            "FROM LikedEntity l " +
            "WHERE l.nickname = :nickname AND l.road_name = :roadName")
    boolean existsByNicknameAndRoadName(@Param("nickname") String nickname, @Param("roadName") String roadName);

    @Modifying
    @Query("DELETE FROM LikedEntity l WHERE l.nickname = :nickname AND l.road_name IN (SELECT mr.address FROM MaemulRegEntity mr WHERE mr.nickname = :nickname)")
    void deleteLikedEntitiesByNicknameAndRoadName(@Param("nickname") String nickname);

    /*김재환 페이징 처리*/
    @Query("SELECT l FROM LikedEntity l " +
            "JOIN MaemulRegEntity mr ON l.nickname = mr.nickname " +
            "JOIN MemberEntity m ON l.nickname = m.nickname " +
            "WHERE l.nickname = :nickname")
    List<LikedEntity> findLikedByNicknameAndPaging(@Param("nickname") String nickname, Pageable pageable);

    /** 관심매물 삭제 */
//    @Override
//    void delete(LikedEntity entity);

    /** nickname과 road_name으로 이미 있는 관심매물인지 조회 */
//    Optional<LikedEntity> findByNicknameAndRoad_name(String nickname, String road_name);

}
//(깃머지충돌)