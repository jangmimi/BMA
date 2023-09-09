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

    /** 김재환작성  관심매물 조회*/
    @Query("SELECT mr FROM LikedEntity l JOIN MaemulRegEntity mr ON l.maemul_id = mr.id WHERE l.nickname = :nickname")
    Page<MaemulRegEntity> findLikedByNicknameAndPaging(@Param("nickname") String nickname, Pageable pageable);

    /** 매물관리 페이지에서 매물 삭제 */
    @Modifying
    @Query("DELETE FROM MaemulRegEntity l " +
            "WHERE l.id = :id " +
            "AND l.nickname = :nickname")
    int deleteMByIdAndNickname(@Param("id") Integer id, @Param("nickname") String nickname);

    /** 키워드 검색시 해당 주소 or 아파트 불러오기 */
    @Query("SELECT m FROM MaemulRegEntity m WHERE replace(m.address, ' ', '') = ?1 or replace(m.APT_name, ' ', '') = ?1")
    List<MaemulRegEntity> findByMaemulKeyword(String keyword);

    /** 김재환작성 검색한 관심매물 조회*/
    @Query("SELECT mr FROM LikedEntity l JOIN MaemulRegEntity mr ON l.maemul_id = mr.id WHERE (mr.address LIKE %:keyword% OR mr.tradeType LIKE %:keyword%) AND l.nickname = :nickname")
    Page<MaemulRegEntity> findSearchMaemul(@Param("keyword") String keyword,@Param("nickname") String nickname, Pageable pageable);

//    /*김재환작성 검색한 관심매물 전체개수*/
//    @Query("SELECT count(mr.id) FROM LikedEntity l JOIN MaemulRegEntity mr ON l.maemul_id = mr.id AND (mr.address LIKE %:keyword% OR mr.tradeType LIKE %:keyword%)")
//    Long searchCountLikedByNickname(@Param("nickname") String nickname,@Param("keyword") String keyword);



}

