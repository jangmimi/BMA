package com.ap4j.bma.model.entity.meamulReg;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "PropertyFeatures")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PropertyFeatures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int featuresID; //특징식별번호 -기본키
    private int maemulID; //매물ID -외래키

    private boolean shortTerm; //단기임대
    private boolean newBuild; //신축
    private boolean investigation; //투자
    private boolean fullOption; //풀옵션
    private boolean elevator; //엘베
    private boolean safety; //보안/안전
    private boolean separate; //분리형
    private boolean parking; //주차


}
