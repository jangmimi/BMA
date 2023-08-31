package com.ap4j.bma.model.entity.member;

import lombok.*;

@Data
@NoArgsConstructor
public class MemberDTO {	// 회원가입 폼 입력 정보 담는 DTO

	private Long id;
	private String email;
	private String name;
	private String pwd;
	private String nickname;
	private String tel;
	private int root;				// 가입 경로는 기본 기본회원 1
	private Boolean choice1 = false;		// 약관 동의 1, 2는 기본 false
	private Boolean choice2 = false;
	private Boolean member_leave = false;	// 탈퇴 여부는 기본 false

	public MemberEntity toEntity() {
		return MemberEntity.builder()
				.id(id)
				.email(email)
				.name(name)
				.pwd(pwd)
				.nickname(nickname)
				.tel(tel)
				.root(root)
				.choice1(choice1)
				.choice2(choice2)
				.member_leave(member_leave)
				.build();
	}

	@Builder
	public MemberDTO(Long id, String email, String name, String nickname, String pwd, String tel,
					 int root, Boolean choice1, Boolean choice2, Boolean member_leave) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.nickname = nickname;
		if(pwd != null) { this.pwd = pwd; }
		this.tel = tel;
		this.root = root;
		this.choice1 = choice1;
		this.choice2 = choice2;
		this.member_leave = member_leave;
	}

	/** 회원정보 수정 */
	public void updateEntity(MemberEntity memberEntity) {
		memberEntity.setName(this.name);
		memberEntity.setNickname(this.nickname);
		memberEntity.setName(this.name);
		memberEntity.setTel(this.tel);
		memberEntity.setPwd(this.pwd);
		memberEntity.setChoice1(this.choice1);
		memberEntity.setChoice2(this.choice2);
	}
}

/*
@Component
public class MemberDTO {

	private Long id; // 기본 키 값
	private String user_id; // 유저 아이디
}
*/