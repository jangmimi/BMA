package com.ap4j.bma.model.entity.member;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {	// 회원가입 폼 입력 정보 담는 DTO

	@NotBlank(message = "이메일을 입력해주세요.")
	@Email(message = "이메일 형식으로 입력해주세요.")
	private String email;

	@NotBlank(message = "이름은 필수 입력사항입니다.")
	private String name;

	@NotBlank(message = "비밀번호를 입력해주세요.")
//	@Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,20}",
//			message = "영문, 숫자를 조합하여 8~20자 이내로 입력해주세요.")
	private String pwd;

	private String nickname;

	private String tel;

	private String root;
	
	// 약관동의여부 값 추가예정

	// 빌더 패턴으로 객체 생성
	public MemberEntity toEntity() {
		return MemberEntity.builder()
				.idx(null)
				.email(email)
				.name(name)
				.pwd(pwd)
				.nickname(nickname)
				.tel(tel)
				.root(root)
				.build();
	}

	@Builder
	public MemberDTO(String name, String pwd, String nickname, String tel) {
		this.name = name;
		this.pwd = pwd;
		this.nickname = nickname;
		this.tel = tel;
	}
}



/*
@Component
public class MemberDTO {

	private Long id; // 기본 키 값
	private String user_id; // 유저 아이디
}
*/