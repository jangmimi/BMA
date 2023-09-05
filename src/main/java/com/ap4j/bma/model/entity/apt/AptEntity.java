package com.ap4j.bma.model.entity.apt;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@Table(name = "apartmentList")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
@Component
public class AptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "road_name", nullable = false, length = 255)
    private String roadName; // 도로명

    @Column(name = "district", nullable = false, length = 255)
    private String district; // 구주소

    @Column(name = "address", nullable = false, length = 255)
    private String address; // 번지

    @Column(name = "apartment_name", nullable = false, length = 255)
    private String apartmentName; // 아파트이름

    @Column(name = "longitude", nullable = false, precision = 10, scale = 6)
    private Double longitude; // 경도

    @Column(name = "latitude", nullable = false, precision = 10, scale = 6)
    private Double latitude; // 위도

}
