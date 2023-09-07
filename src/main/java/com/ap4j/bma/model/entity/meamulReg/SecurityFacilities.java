package com.ap4j.bma.model.entity.meamulReg;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "SecurityFacilities")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SecurityFacilities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int securityID; //옵션식별번호 -기본키

    private int maemulID; //매물ID -외래키

    private boolean securityEntrance; //현관보안
    private boolean CCTV; //cctv
    private boolean securityBars; //방범창
    private boolean securityGuard; //경비원
    private boolean fireAlarm; //화재경보기
    private boolean videoPhone; //비디오폰
    private boolean intercom; //인터폰
    private boolean keyCard; //카드키
    private boolean privateSecurity; //사설경비
    private boolean digitalDoorLock; //전자도어락
}