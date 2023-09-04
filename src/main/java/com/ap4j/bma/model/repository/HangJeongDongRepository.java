package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.apt.HangJeongDongEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HangJeongDongRepository extends JpaRepository<HangJeongDongEntity, Long> {
    /** 화면 좌표값에 해당하는 행정동 리스트 불러오기 */
    @Query("SELECT hjd FROM HangJeongDongEntity hjd WHERE hjd.latitude >= ?1 AND hjd.latitude <= ?3 AND hjd.longitude >= ?2 AND hjd.longitude <= ?4")
    List<HangJeongDongEntity> findHJDListBounds(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng);

    /** 줌레벨 6 (동 호출) */
    @Query("SELECT hjd FROM HangJeongDongEntity hjd WHERE hjd.eupMyeonDong is not null AND hjd.latitude >= ?1 AND hjd.latitude <= ?3 AND hjd.longitude >= ?2 AND hjd.longitude <= ?4")
    List<HangJeongDongEntity> findHJDListZoomLevel6(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng);

    /** 줌레벨 7 (구 호출) */
    @Query("SELECT hjd FROM HangJeongDongEntity hjd WHERE hjd.eupMyeonDong is null AND hjd.siGunGu is not null AND hjd.latitude >= ?1 AND hjd.latitude <= ?3 AND hjd.longitude >= ?2 AND hjd.longitude <= ?4")
    List<HangJeongDongEntity> findHJDListZoomLevel7(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng);

    /** 줌레벨 8이상 (시 도 호출) */
    @Query("SELECT hjd FROM HangJeongDongEntity hjd WHERE hjd.siGunGu is null")
    List<HangJeongDongEntity> findHJDListZoomLevel8();

}
