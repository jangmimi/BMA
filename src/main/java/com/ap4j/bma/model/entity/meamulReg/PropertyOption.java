package com.ap4j.bma.model.entity.meamulReg;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "PropertyOption")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PropertyOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int optionsID; //옵션식별번호 -기본키
    private int maemulID; //매물ID -외래키

    private boolean wallHanging; //벽걸이형
    private boolean bed; //침대
    private boolean desk; //책상
    private boolean closet; //옷장
    private boolean shoeCloset; //신발장
    private boolean builtInCloset; //붙박이장
    private boolean eatingTable; //식탁
    private boolean sofa; //소파
    private boolean ceilingAirConditioner; //천장형 에어컨
    private boolean manlessDeliveryBox; //무인택배함
    private boolean bidet; //비데
    private boolean refrigerator; //냉장고
    private boolean washingMachine; //세탁기
    private boolean showerBooth; //샤워부스
    private boolean sink; //싱크대
    private boolean induction; //인덕션
    private boolean microwave; //전자레인지
    private boolean tv; //TV
    private boolean airConditioner; //에어컨

}
