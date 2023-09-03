package com.ap4j.bma.model.entity.apt;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "apt_transactions")
@Data // Lombok을 사용하여 Getter, Setter 등을 자동 생성
public class AptTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "deal_amount")
	private String dealAmount;

	@Column(name = "deal_type")
	private String dealType;

	@Column(name = "build_year")
	private String buildYear;

	@Column(name = "year")
	private String year;

	@Column(name = "road_name")
	private String roadName;

	@Column(name = "road_name_main_code")
	private String roadNameMainCode;

	@Column(name = "road_name_sub_code")
	private String roadNameSubCode;

	@Column(name = "road_name_gu_code")
	private String roadNameGuCode;

	@Column(name = "road_name_serial_code")
	private String roadNameSerialCode;

	@Column(name = "road_name_ground_code")
	private String roadNameGroundCode;

	@Column(name = "road_name_code")
	private String roadNameCode;

	@Column(name = "registration_date")
	private String registrationDate;

	@Column(name = "legal_dong")
	private String legalDong;

	@Column(name = "legal_dong_main_number_code")
	private String legalDongMainNumberCode;

	@Column(name = "legal_dong_sub_number_code")
	private String legalDongSubNumberCode;

	@Column(name = "legal_dong_city_code")
	private String legalDongCityCode;

	@Column(name = "legal_dong_town_code")
	private String legalDongTownCode;

	@Column(name = "legal_dong_serial_code")
	private String legalDongSerialCode;

	@Column(name = "apartment")
	private String apartment;

	@Column(name = "month")
	private String month;

	@Column(name = "day")
	private String day;

	@Column(name = "serial_number")
	private String serialNumber;

	@Column(name = "exclusive_area")
	private String exclusiveArea;

	@Column(name = "agent_location")
	private String agentLocation;

	@Column(name = "land_lot")
	private String landLot;

	@Column(name = "region_code")
	private String regionCode;

	@Column(name = "floor")
	private String floor;

	@Column(name = "release_reason_date")
	private String releaseReasonDate;

	@Column(name = "release_status")
	private String releaseStatus;

}