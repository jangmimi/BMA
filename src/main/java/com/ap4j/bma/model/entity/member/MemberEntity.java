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
	private Long idx;	// 기본 키(DB PK)
	@Column
	private String email;
	@Column
	private String name;
	@Column
	private String pwd;
	@Column
	private String nickname;
	@Column
	private String tel;
	@Column
	private String root;	// 가입 경로
	// 약관동의여부 값 추가예정

	@Builder	// 빌더 패턴 적용
	public MemberEntity(Long idx, String email, String name, String pwd, String nickname, String tel, String root) {
		this.idx = idx;
		this.email = email;
		this.name =  name;
		this.pwd = pwd;
		this.nickname = nickname;
		this.tel = tel;
		this.root = root;
	}

//	public static MemberDTO toDTO(MemberEntity entity) {
//		return MemberDTO.builder()
//				.idx(entity.getIdx())
//				.email(entity.getEmail())
//				.name(entity.getName())
//				.pwd(entity.getPwd())
//				.nickname(entity.getNickname())
//				.tel(entity.getTel())
//				.root(entity.getRoot())
//				.build();
//	}

	/** 회원정보 수정 */
	public void update(String name, String nickname, String tel, String pwd) {
		this.name = name;
		this.nickname = nickname;
		this.tel = tel;
		this.pwd = pwd;
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