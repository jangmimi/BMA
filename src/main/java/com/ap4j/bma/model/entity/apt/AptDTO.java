package com.ap4j.bma.model.entity.apt;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AptDTO {
    private String aptName;    // 아파트 이름
    private String aptCityAddress; // 시 주소
    private String aptAddress1; // 아파트 법정동
    private String aptAddress2; // 아파트 지번
    private String aptDealAmount; // 아파트 실 거래가
//    private String aptLng;     // 아파트 경도
//    private String aptLat;     // 아파트 위도
}
