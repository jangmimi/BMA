package com.ap4j.bma.model.entity.apt;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.Column;

@Component
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AptDTO {
    private String dealAmount; // 거래금액
    private String dealType; // 거래유형
    private String buildYear; // 건축년도
    private String year; // 년
    private String roadName; // 도로명
    private String roadNameMainCode; // 도로명건물본번호코드
    private String roadNameSubCode; // 도로명건물부번호코드
    private String roadNameGuCode; // 도로명시군구코드
    private String roadNameSerialCode; // 도로명일련번호코드
    private String roadNameGroundCode; // 도로명지상지하코드
    private String roadNameCode; // 도로명코드
    private String registrationDate; // 등기일자
    private String legalDong; // 법정동
    private String legalDongMainNumberCode; // 법정동본번코드
    private String legalDongSubNumberCode; // 법정동부번코드
    private String legalDongCityCode; // 법정동시군구코드
    private String legalDongTownCode; // 법정동읍면동코드
    private String legalDongSerialCode; // 법정동지번코드
    private String apartment; // 아파트
    private String month; // 월
    private String day; // 일
    private String serialNumber; // 일련번호
    private String exclusiveArea; // 전용면적
    private String agentLocation; // 중개사소재지
    private String landLot; // 지번
    private String regionCode; // 지역코드
    private String floor; // 층
    private String releaseReasonDate; // 해제사유발생일
    private String releaseStatus; // 해제여부
}
