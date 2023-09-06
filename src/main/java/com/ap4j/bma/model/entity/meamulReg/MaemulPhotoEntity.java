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
    private int PhotoID; //사진식별번호 - 기본키
    private int maemulID; //매물ID - 외래키

    private String primeName; //대표 사진 이름
    private String primePhoto; // 대표 사진 경로

    private String twoName;  // 2번째 사진
    private String photoTwo;

    private String threeName; // 3번째 사진
    private String photoThree;

    private String fourName; // 4번째 사진
    private String photoFour;

    private String fiveName; // 5번째 사진
    private String photoFive;

}
