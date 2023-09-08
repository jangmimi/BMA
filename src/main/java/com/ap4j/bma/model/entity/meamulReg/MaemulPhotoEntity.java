package com.ap4j.bma.model.entity.meamulReg;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "MaemulPhoto")
@ToString
@Getter
@Setter
public class MaemulPhotoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int photoID;
    private Integer maemulID; // 매물 ID - 외래키..

    private String photoPath; // 사진 경로

}
