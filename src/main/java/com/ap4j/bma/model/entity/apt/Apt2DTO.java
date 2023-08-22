package com.ap4j.bma.model.entity.apt;


import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Apt2DTO {
	private String aptName;    // 아파트 이름
	private String aptAddress; // 시 주소
	private String aptLng;     // 아파트 경도
	private String aptLat;     // 아파트 위도


}