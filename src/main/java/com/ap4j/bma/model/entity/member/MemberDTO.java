package com.ap4j.bma.model.entity.member;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
public class MemberDTO {
	private String email;
	private String name;
	private String pwd;

	// 빌더 패턴으로 객체 생성
	public MemberEntity toEntity() {
		return MemberEntity.builder()
				.idx(null)
				.email(email)
				.name(name)
				.pwd(pwd)
				.build();
	}
}



/*
@Component
public class MemberDTO {

	private Long id; // 기본 키 값
	private String user_id; // 유저 아이디
}
*/