package com.ap4j.bma.model.entity.member;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name="liked") // DB 테이블 이름 지정
@Entity
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class InterestMaemulEntity {
    @Id
    @NotNull
    private Boolean liked;
    @NotNull
    private String email;
    @NotNull
    private String road_name;

    @Builder
    public InterestMaemulEntity(Boolean liked, String email, String road_name) {
        this.liked = liked;
        this.email = email;
        this.road_name = road_name;
    }

    public InterestMaemulDTO toDTO() {
        return InterestMaemulDTO.builder()
                .liked(liked)
                .email (email)
                .road_name(road_name)
                .build();
    }
}
