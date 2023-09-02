package com.ap4j.bma.model.entity.apt;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "hangJeongDong")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
@Builder
public class HangJeongDongEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "si_do")
    private String siDo;

    @Column(name = "si_gun_gu")
    private String siGunGu;

    @Column(name = "eup_myeon_dong")
    private String eupMyeonDong;

    @Column(name = "eup_myeon_ri_dong")
    private String eupMyeonRiDong;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;
}
