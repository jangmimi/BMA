package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaemulRegRepository extends JpaRepository<MaemulRegEntity, Integer> {

    /** 현재 보고있는 화면의 좌표값에 해당하는 매물 리스트 불러오기 */
    @Query("SELECT m FROM MaemulRegEntity m WHERE m.latitude >= ?1 AND m.latitude <= ?3 AND m.longitude >= ?2 AND m.longitude <= ?4")
    List<MaemulRegEntity> findMaemulListBounds(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng);

    /** id기준으로 매물 조회 */

    Optional<MaemulRegEntity> findMaemulById(Integer id);

    /** 로그인한 멤버 nickname이랑 매치되는 매물 리스트 불러오기 */
    @Query("SELECT mr FROM MaemulRegEntity mr " +
            "JOIN MemberEntity m ON mr.nickname = m.nickname " +
            "WHERE m.nickname = :nickname")
    List<MaemulRegEntity> findMaemulByMemberNickname(@Param("nickname") String nickname);

}