package com.ap4j.bma.model.entity.member;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Table(name="recent") // DB 테이블 이름 지정
@Entity
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class RecentEntity {
    @Id
    @NotNull
    private String road_name;
    @NotNull
    private String email;

    @Builder
    public RecentEntity(String road_name, String email) {
        this.road_name = road_name;
        this.email = email;
    }

    public RecentDTO toDTO() {
        return RecentDTO.builder()
                .road_name(road_name)
                .email (email)
                .build();
    }
}

