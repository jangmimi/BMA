package com.ap4j.bma.model.entity.member;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {	// 회원가입 폼에 입력한 정보들을 담는 DTO

	@NotBlank(message = "이메일은 필수 입력사항입니다.")
//	@Email(message = "이메일 형식에 맞지 않습니다.")
	private String email;

	@NotBlank(message = "이름은 필수 입력사항입니다.")
	private String name;

	@NotBlank(message = "비밀번호는 필수 입력사항입니다.")
	@Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,20}",
			message = "비밀번호는 영문 대,소문자와 숫자가 적어도 1개 이상씩 포함된 8~20자로 입력해주세요.")
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