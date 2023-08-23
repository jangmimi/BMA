package com.ap4j.bma.model.entity.apt;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "apttest")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class AptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String aptName;         // 아파트 이름
    @Column
    private String aptAddress;      // 아파트 주소
    @Column
    private String kaptCode;        // 아파트 코드
    @Column
    private String bjdCode;         // 법정동 코드
    @Column
    private String latitude;        // 위도
    @Column
    private String longitude;       // 경도

}
