package com.ap4j.bma.model.entity.member;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name="liked") // DB 테이블 이름 지정
@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class LikedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "nickname", referencedColumnName = "nickname")
    @NotNull
    private String nickname;

    private Integer maemul_id;

    @Builder
    public LikedEntity(Long id, String nickname, String road_name, Integer maemul_id) {
        this.id = id;
        this.nickname = nickname;
        this.maemul_id = maemul_id;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nickname", referencedColumnName = "nickname", insertable = false, updatable = false)
    private MemberEntity memberEntity;

}
