package com.ap4j.bma.model.entity.apt;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "aptDetail")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class AptDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "district", nullable = false, length = 255)
    private String district; // 구주소

    @Column(name = "address", nullable = false, length = 255)
    private String address; // 번지

    @Column(name = "complex_name", nullable = false, length = 255)
    private String complexName; // 아파트이름

    @Column(name = "area", nullable = false, precision = 10, scale = 2)
    private Double area; // 면적

    @Column(name = "contract_year_month", nullable = false, length = 6)
    private String contractYearMonth; // 계약년월

    @Column(name = "contract_date", nullable = false, length = 10)
    private String contractDate; // 계약일

    @Column(name = "transaction_amount", nullable = false)
    private Integer transactionAmount; // 계약금액

    @Column(name = "floor", nullable = false)
    private Integer floor; // 층수

    @Column(name = "construction_year", nullable = false)
    private Integer constructionYear; // 건축년도

    @Column(name = "road_name", nullable = false, length = 255)
    private String roadName; // 도로명
}
