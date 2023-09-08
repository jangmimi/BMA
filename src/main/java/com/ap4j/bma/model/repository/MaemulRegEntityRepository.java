package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MaemulRegEntityRepository extends JpaRepository<MaemulRegEntity, Integer>, MaemulRepositoryCustom {



    /** 현재 보고있는 화면의 좌표값에 해당하는 매물 리스트 불러오기 */
//    @Query("SELECT m FROM MaemulRegEntity m WHERE m.latitude >= ?1 AND m.latitude <= ?3 AND m.longitude >= ?2 AND m.longitude <= ?4")
//    List<MaemulRegEntity> findMaemulListBounds(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng);

    /** id기준으로 매물 조회 */

    Optional<MaemulRegEntity> findMaemulById(Integer id);

    /**  마커 클릭시 해당 주소값과 같은 매물 리스트 불러오기 */
    @Query("select m from MaemulRegEntity m WHERE m.address = ?1")
    List<MaemulRegEntity> findMaemulByAddress(String address);


    /** 로그인한 멤버 nickname이랑 매치되는 매물 리스트 불러오기 */
    @Query("SELECT mr FROM MaemulRegEntity mr " +
            "JOIN MemberEntity m ON mr.nickname = m.nickname " +
            "WHERE m.nickname = :nickname")
    List<MaemulRegEntity> findMaemulByMemberNickname(@Param("nickname") String nickname);

    List<MaemulRegEntity> findByMemberEntity_Nickname(String nickname);

    @Query("SELECT COUNT(m) FROM MaemulRegEntity m WHERE (m.buildingUsage = '거주용' OR m.buildingUsage = '거주/업무용') AND m.nickname = :nickname")
    Long countResidential(@Param("nickname") String nickname);

    @Query("SELECT COUNT(m) FROM MaemulRegEntity m WHERE (m.buildingUsage = '업무용' OR m.buildingUsage = '거주/업무용') AND m.nickname = :nickname")
    Long countCommercial(@Param("nickname") String nickname);

    /** 김재환작성*/
    @Query(value = "SELECT mr.id, mr.APT_name, mr.elevator, mr.parking, mr.selling_price, mr.address, mr.available_move_in_date, mr.building_usage, mr.content, mr.created_at, mr.deposit_for_lease, mr.direction, mr.features, mr.floor_number, mr.heating_type, mr.latitude, mr.loan_amount, mr.longitude, mr.management_fee, mr.monthly_for_rent, mr.monthly_rent, l.nickname, mr.number_of_bathrooms, mr.number_of_rooms, mr.optional, mr.private_area, mr.security, mr.short_term_rental, mr.supply_area, mr.title, mr.total_floors, mr.total_parking, mr.trade_type, mr.d_management_fee, mr.m_management_fee, mr.adress " +
            "FROM liked l " +
            "JOIN maemul_reg mr ON l.road_name = mr.address " +
            "WHERE l.nickname = :nickname", nativeQuery = true)
    Page<MaemulRegEntity> findLikedByNicknameAndPaging(@Param("nickname") String nickname, Pageable pageable);

    /** 매물관리 페이지에서 매물 삭제 */
    @Modifying
    @Query("DELETE FROM MaemulRegEntity l " +
            "WHERE l.id = :id " +
            "AND l.nickname = :nickname")
    int deleteMByIdAndNickname(@Param("id") Integer id, @Param("nickname") String nickname);

}

