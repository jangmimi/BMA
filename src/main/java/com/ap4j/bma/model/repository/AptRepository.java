package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.apt.AptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AptRepository extends JpaRepository<AptEntity, Long> {

    /** 카카오지도 API에서 가져온 아파트 이름으로 좌표 저장해줄 때 사용 */
    @Query("SELECT a FROM AptEntity a WHERE a.roadName = ?1")
    AptEntity findByRoadName(String roadName);

    /** 화면 좌표값에 해당하는 아파트 리스트 불러오기 */
    @Query("SELECT apt FROM AptEntity apt WHERE apt.latitude >= ?1 AND apt.latitude <= ?3 AND apt.longitude >= ?2 AND apt.longitude <= ?4")
    List<AptEntity> findAptListBounds(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng);

    /** 키워드 검색 아파트 불러오기 */
    @Query("SELECT a FROM AptEntity a WHERE replace(a.roadName, ' ', '') = ?1 or replace(a.apartmentName, ' ', '') = ?1")
    AptEntity findByKeyword(String keyword);

}