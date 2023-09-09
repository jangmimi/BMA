package com.ap4j.bma.model.entity.recent;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.entity.member.MemberEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "recent")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
public class RecentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // MAEMUL_ID에 대한 매핑
    @ManyToOne
    @JoinColumn(name = "MAEMUL_ID", referencedColumnName = "ID")
    private MaemulRegEntity maemulEntity;

    // MemberEntity와의 일대일 관계 설정 (nickname을 기준으로 매핑)
    @ManyToOne
    @JoinColumn(name = "MEMBER_NICKNAME", referencedColumnName = "nickname")
    private MemberEntity memberEntity;

}
