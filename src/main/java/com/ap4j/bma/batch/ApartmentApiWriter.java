package com.ap4j.bma.batch;

import com.ap4j.bma.model.entity.apt.AptBatchDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class ApartmentApiWriter implements ItemWriter<List<AptBatchDTO>> {


	private final ApartmentApiService apartmentApiService;
	private final JdbcTemplate jdbcTemplate;

	//	@Override
	//	public void write(List<? extends List<AptDTO>> items) throws Exception {
	//		items.stream()
	//				.forEach(aptList -> apartmentApiService.fetchDataFromApi());
	//	}
	@Override
	public void write(List<? extends List<AptBatchDTO>> items) throws Exception {
		log.info("write 실행됐다@@@@");

		/*
		* todo
		*  중복 DB가 여러번 저장되고 조회되는 거 수정해야함. 성능에 무리. 지금은 임시로 Set으로 중복 제거중.
		* */
		Set<AptBatchDTO> uniqueAptSet = new HashSet<>();

		for (List<AptBatchDTO> aptList : items) {
			for (AptBatchDTO aptBatchDTO : aptList) {
				// 이미 저장한 데이터인지 확인
				if (!uniqueAptSet.contains(aptBatchDTO)) {
					// 아파트 데이터를 MySQL 데이터베이스에 저장
					saveAptDataToDatabase(aptBatchDTO);
					// 이미 저장한 데이터로 표시
					uniqueAptSet.add(aptBatchDTO);
				}
			}
		}
	}

	private void saveAptDataToDatabase(AptBatchDTO aptBatchDTO) {
		String sql = "INSERT INTO apt_transactions (deal_amount, deal_type, build_year, year, road_name, " +
				"road_name_main_code, road_name_sub_code, road_name_gu_code, road_name_serial_code, " +
				"road_name_ground_code, road_name_code, registration_date, legal_dong, " +
				"legal_dong_main_number_code, legal_dong_sub_number_code, legal_dong_city_code, " +
				"legal_dong_town_code, legal_dong_serial_code, apartment, month, day, serial_number, " +
				"exclusive_area, agent_location, land_lot, region_code, floor, " +
				"release_reason_date, release_status)" +
				" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			jdbcTemplate.update(
					sql,
					aptBatchDTO.getDealAmount(),
					aptBatchDTO.getDealType(),
					aptBatchDTO.getBuildYear(),
					aptBatchDTO.getYear(),
					aptBatchDTO.getRoadName(),
					aptBatchDTO.getRoadNameMainCode(),
					aptBatchDTO.getRoadNameSubCode(),
					aptBatchDTO.getRoadNameGuCode(),
					aptBatchDTO.getRoadNameSerialCode(),
					aptBatchDTO.getRoadNameGroundCode(),
					aptBatchDTO.getRoadNameCode(),
					aptBatchDTO.getRegistrationDate(),
					aptBatchDTO.getLegalDong(),
					aptBatchDTO.getLegalDongMainNumberCode(),
					aptBatchDTO.getLegalDongSubNumberCode(),
					aptBatchDTO.getLegalDongCityCode(),
					aptBatchDTO.getLegalDongTownCode(),
					aptBatchDTO.getLegalDongSerialCode(),
					aptBatchDTO.getApartment(),
					aptBatchDTO.getMonth(),
					aptBatchDTO.getDay(),
					aptBatchDTO.getSerialNumber(),
					aptBatchDTO.getExclusiveArea(),
					aptBatchDTO.getAgentLocation(),
					aptBatchDTO.getLandLot(),
					aptBatchDTO.getRegionCode(),
					aptBatchDTO.getFloor(),
					aptBatchDTO.getReleaseReasonDate(),
					aptBatchDTO.getReleaseStatus()
			);
			log.info("Apt Data Saved to Database: {}", aptBatchDTO);
		} catch (DataAccessException e) {
			log.error("Error saving Apt Data to Database: {}", e.getMessage());
		}
	}

}
