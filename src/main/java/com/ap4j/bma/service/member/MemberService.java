package com.ap4j.bma.service.member;

import com.ap4j.bma.model.entity.member.MemberEntity;

import java.util.HashMap;

public interface MemberService {	// pjm Member 기 능 작 업 중

	/** 토큰 얻기 */
	public String getAccessToken(String code);
	/** 카카오 유저 정보 얻기 */
	public HashMap<String, Object> getUserInfo(String accessToken);	// HashMap -> MemberDTO로 변경 예정
	/** 카카오 로그아웃 */
	public void kakaoLogout(String accessToken);
	/** 기본 회원가입 */
	public Long joinBasic(MemberEntity pMember);


	/** 중복회원 검증 */
//	public void validateDuplicateMember(MemberEntity pMember);

	void addSomething(String something);

}
