package com.ap4j.bma.model.entity.meamulReg;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MaemulRegEntity {

    //매물등록페이지
    @Id
    private Integer id; //매물 등록 번호

    private String adress; //도로명 주소
    private String APT_name; //단지명
    private int APT_number; // 아파트 동
    private int home_number; // 아파트 호수

    private String buildingUsage; //건축물 용도
    private int numberOfRooms; //방 수
    private int numberOfBathrooms; //욕실 수
    private int floorNumber; //해당층
    private int totalFloors; //건물층
    private int privateArea; //전용면적
    private int supplyArea; //공급면적
    private String direction; //방향
    private String heatingType;//난방종류
    private int Elevator;//엘리베이터(value를 1,0으로 받아오면 int값으로 저장될 거 같아서 int로함)
    private int Parking;//주차가능여부 (엘베랑 같은 이유)
    private int totalParking;//총 주차대수
    private int shortTermRental;//단기임대
    private String availableMoveInDate;//입주가능일
    private Long loanAmount;//융자금

    //월세
    private Long monthlyForRent; //희망보증금
    private Long monthlyRent; //희망월세
    private Long m_managementFee;//관리비


    //전세
    private Long depositForLease; //희망 전세
    private Long d_managementFee; //관리비

    //매매
    private Long SellingPrice;//희망매매가

    //매물 이미지
    private String title;//상세 설명 제목
    private String content; //상세설명 내용

    private String features;//특징
    private String options;//옵션
    private String security; //보안/안전시설
    private String location; //위치 및 주변시설
    private String school;//학군정보
}
