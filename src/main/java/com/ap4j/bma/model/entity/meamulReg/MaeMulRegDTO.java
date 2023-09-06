package com.ap4j.bma.model.entity.meamulReg;


import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MaeMulRegDTO {

    private Integer id; //매물 등록 번호
    private String nickname; //등록한 사람 정보 , 유니크키

    //매물 등록 페이지
    private String address; //도로명 주소
    private String APT_name; //아파트 이름

    private String buildingUsage; //건축물 용도
    private int numberOfRooms; //방 수
    private int numberOfBathrooms; //욕실 수
    private int floorNumber; //해당층
    private int totalFloors; //건물층
    private Double privateArea; //전용면적
    private Double supplyArea; //공급면적
    private String direction; //방향
    private String heatingType;//난방종류
    private String Elevator;//엘리베이터(value를 1,0으로 받아오면 int값으로 저장될 거 같아서 int로함)
    private String Parking;//주차가능여부 (엘베랑 같은 이유)
    private int totalParking;//총 주차대수
    private String shortTermRental;//단기임대
    private String availableMoveInDate;//입주가능일
    private Long loanAmount;//융자금


    //거래 유형
    private String tradeType;//거래 유형

    //월세
    private Long monthlyForRent; //희망보증금
    private Long monthlyRent; //희망월세
    private Long m_managementFee;//관리비


    //전세
    private Long depositForLease; //희망 전세
    private Long d_managementFee; //관리비

    //매매
    private Long SellingPrice;//희망매매가

    //상세정보 페이지
    private String title; //제목
    private String content; //상세정보

    //체크박스
    private String features; //특징
    private String optional;//옵션
    private String security;//보안, 안전시설

    //매물 이미지 추가

    //좌표
    private Double longitude; //경도
    private Double latitude; //위도
}
