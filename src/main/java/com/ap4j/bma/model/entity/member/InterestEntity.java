package com.ap4j.bma.model.entity.member;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name="liked") // DB 테이블 이름 지정
@Entity
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class InterestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String email;
    @NotNull
    private String road_name;

    @Builder
    public InterestEntity(Long id, String email, String road_name) {
        this.id = id;
        this.email = email;
        this.road_name = road_name;
    }

    public InterestlDTO toDTO() {
        return InterestlDTO.builder()
                .id(id)
                .email (email)
                .road_name(road_name)
                .build();
    }
}
