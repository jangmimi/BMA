package com.ap4j.bma.model.entity.apt;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AptRealTradeDTO {
    private String district;            // 구주소
    private String address;             // 번지
    private String complexName;         // 아파트 이름
    private Double area;                // 면적
    private String contractYearMonth;   // 판매년월
    private String contractDate;        // 판매일
    private Integer transactionAmount;  // 판매금액
    private Integer floor;              // 층수일
    private Integer constructionYear;   // 건설년도
    private String roadName;            // 도로명
}
