package com.ap4j.bma.model.entity.member;

import lombok.*;

import javax.persistence.*;

@Table(name="member") // DB 테이블 이름 지정
@Entity
@NoArgsConstructor
//@AllArgsConstructor
@Getter
@Setter
@ToString
public class MemberEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)	// Idx 자동 증가
	private Long id;	// 기본 키(DB PK)
	@Column(unique = true)
	private String email;
	@Column
	private String name;
	@Column
	private String pwd;
	@Column(unique = true)
	private String nickname;
	@Column
	private String tel;
	@Column
	private int root;	// 가입 경로
	@Column
	private Boolean choice1;	// 약관 동의 선택 여부
	@Column
	private Boolean choice2;
	@Column
	private Boolean member_leave;	// 회원 탈퇴 여부


	@Builder	// 빌더 패턴 적용
	public MemberEntity(Long id, String email, String name, String pwd, String nickname,
						String tel, int root, Boolean choice1, Boolean choice2, Boolean member_leave) {
		this.id = id;
		this.email = email;
		this.name =  name;
		this.pwd = pwd;
		this.nickname = nickname;
		this.tel = tel;
		this.root = root;
		this.choice1 = choice1;
		this.choice2 = choice2;
		this.member_leave = member_leave;
	}

	public MemberDTO toDTO() {
		return MemberDTO.builder()
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
}

// DATABASE (MySQL) TIP!!
// 컬럼 순서 바꾸기
// alter table member modify column root varchar(2) after tel;
//				테이블명				바꿀컬럼명 타입 ~뒤로 컬럼명(얘 뒤로)

/*
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
*/