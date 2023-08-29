package com.ap4j.bma.model.entity.apt;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "apt")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class AptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "district", nullable = false, length = 255)
    private String district; // 구주소

    @Column(name = "address", nullable = false, length = 255)
    private String address; // 번지

    @Column(name = "complex_name", nullable = false, length = 255)
    private String complexName; // 아파트이름

    @Column(name = "road_name", nullable = false, length = 255)
    private String roadName; // 도로명

    @Column(name = "longitude", nullable = false, precision = 10, scale = 6)
    private Double longitude; // 경도

    @Column(name = "latitude", nullable = false, precision = 10, scale = 6)
    private Double latitude; // 위도

}
