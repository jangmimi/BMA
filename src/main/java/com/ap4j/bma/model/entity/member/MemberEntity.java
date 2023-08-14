package com.ap4j.bma.model.entity.member;

import lombok.*;

import javax.persistence.*;

// 이건 JPA 에서 쓰는 자바 테이블 이라고 생각 하시면 됩니다 :) 아래가 예시에요.
@Entity
@Table(name="member") // 명시적으로 테이블 이름 지정.
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // 기본 키

	@Column
	private String user_id; // 유저 아이디

}
