package com.ap4j.bma.model.entity.member;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RecentMaemulDTO {

    private String road_name;
    private String email;

    @Builder
    public  RecentMaemulDTO(String road_name, String email) {
        this.road_name = road_name;
        this.email = email;
    }

    public RecentMaemulEntity toEntity() {
        return RecentMaemulEntity.builder()
                .road_name(road_name)
                .email(email)
                .build();
    }
}
