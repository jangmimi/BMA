package com.ap4j.bma.service.apartment;

import com.ap4j.bma.model.entity.apt.AptDTO;

import java.util.List;

public interface ApartmentService {

	/** DB 데이터 불러오기 (좌표 넣기전) */
	public List<AptDTO> aptList();

	/** DB에 좌표 업데이트 */
	public void updateApt(String roadName, Double latitude, Double longitude);

	/** 보고있는 화면의 좌표 범위의 DB 데이터 불러오기 */
	public List<AptDTO> findAptListBounds(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng);


}
