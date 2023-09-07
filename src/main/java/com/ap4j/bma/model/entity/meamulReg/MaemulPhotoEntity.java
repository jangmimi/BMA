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
    private int photoID; // 사진 식별번호 - 기본키
    private int maemulID; // 매물 ID - 외래키

    // 이미지 경로를 쉼표로 구분하여
    @Column(length = 1000)
    private String imagePaths; // 이미지 경로를 쉼표로 구분하여 저장

    // 이미지 경로를 설정하는 메서드 추가
    public void addImagePath(String imagePath) {
        if (imagePaths == null || imagePaths.isEmpty()) {
            imagePaths = imagePath;
        } else {
            imagePaths += "," + imagePath;
        }
    }

    // 이미지 경로를 배열로 반환하는 메서드
    public String[] getImagePathsArray() {
        if (imagePaths != null && !imagePaths.isEmpty()) {
            return imagePaths.split(",");
        }
        return new String[0]; // 이미지가 없는 경우 빈 배열 반환
    }
}