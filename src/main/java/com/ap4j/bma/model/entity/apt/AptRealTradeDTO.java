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
    private String roadName;            // 도로명
    private Double area;                // 면적
    private String contractYearMonth;   // 거래년월
    private String contractDate;        // 거래일
    private Integer transactionAmount;  // 실거래가
    private Integer floor;              // 층
    private Integer constructionYear;   // 준공년도
}
