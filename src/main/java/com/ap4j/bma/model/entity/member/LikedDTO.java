package com.ap4j.bma.model.entity.member;

import lombok.*;

@Data
@NoArgsConstructor
public class LikedDTO {

    private Long id;
    private String nickname;
    private String road_name;

    @Builder
    public LikedDTO(Long id, String nickname, String road_name) {
        this.id = id;
        this.nickname = nickname;
        this.road_name = road_name;
    }

    public LikedEntity toEntity() {
        return LikedEntity.builder()
                .id(id)
                .nickname(nickname)
                .road_name(road_name)
                .build();
    }
}