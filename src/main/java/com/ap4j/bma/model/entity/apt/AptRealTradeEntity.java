package com.ap4j.bma.model.entity.apt;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "apartmentRealTrade")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class AptRealTradeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "road_name", nullable = false, length = 255)
    private String roadName; // 도로명

    @Column(name = "area", nullable = false, precision = 10, scale = 2)
    private Double area; // 면적

    @Column(name = "contract_year_month", nullable = false, length = 6)
    private String contractYearMonth; // 거래년월

    @Column(name = "contract_date", nullable = false, length = 10)
    private String contractDate; // 거래일

    @Column(name = "transaction_amount", nullable = false)
    private Integer transactionAmount; // 실거래가

    @Column(name = "floor", nullable = false)
    private Integer floor; // 층

    @Column(name = "construction_year", nullable = false)
    private Integer constructionYear; // 준공년도


}
