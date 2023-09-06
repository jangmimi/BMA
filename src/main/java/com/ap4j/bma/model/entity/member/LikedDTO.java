package com.ap4j.bma.model.entity.member;

import lombok.*;

@Data
@NoArgsConstructor
public class LikedDTO {

    private Long id;
    private String email;
    private String road_name;

    @Builder
    public LikedDTO(Long id, String email, String road_name) {
        this.id = id;
        this.email = email;
        this.road_name = road_name;
    }

    public LikedEntity toEntity() {
        return LikedEntity.builder()
                .id(id)
                .email(email)
                .road_name(road_name)
                .build();
    }
}