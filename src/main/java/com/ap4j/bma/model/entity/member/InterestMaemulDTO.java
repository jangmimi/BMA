package com.ap4j.bma.model.entity.member;

import lombok.*;

@Data
@NoArgsConstructor
public class InterestMaemulDTO {

    private Boolean liked;
    private String email;
    private String road_name;

    @Builder
    public  InterestMaemulDTO(Boolean liked, String email, String road_name) {
        this.liked = liked;
        this.email = email;
        this.road_name = road_name;
    }

    public InterestMaemulEntity toEntity() {
        return InterestMaemulEntity.builder()
                .liked(liked)
                .email(email)
                .road_name(road_name)
                .build();
    }
}