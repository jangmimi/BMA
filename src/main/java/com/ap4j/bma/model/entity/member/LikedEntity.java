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

    @NotNull
    private String road_name;

    @Builder
    public LikedEntity(Long id, String nickname, String road_name) {
        this.id = id;
        this.nickname = nickname;
        this.road_name = road_name;
    }

    public LikedDTO toDTO() {
        return LikedDTO.builder()
                .id(id)
                .nickname (nickname)
                .road_name(road_name)
                .build();
    }
}
