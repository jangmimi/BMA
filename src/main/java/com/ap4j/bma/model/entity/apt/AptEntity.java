package com.ap4j.bma.model.entity.apt;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "apttest")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class AptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String district;

    @Column
    private String address;


}
