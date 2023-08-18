package com.ap4j.bma.service.member;

import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.entity.member.MemberEntity;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;

public interface MemberService {	// pjm Member 기 능 작 업 중

	/** 토큰 얻기 */
	public String getAccessToken(String code);

	/** 카카오 유저 정보 얻기 */
	public HashMap<String, Object> getUserInfo(String accessToken);	// HashMap -> MemberDTO로 변경 예정

//	public MemberDTO getUserInfo2(String accessToken);

	/** 카카오 로그아웃 */
	public void kakaoLogout(String accessToken);

	/** 기본 회원가입 */
	public Long joinBasic(MemberEntity pMember);

	/** 중복회원 검증 */
//	public void validateDuplicateMember(MemberEntity pMember);

	/** 회원전체 조회 */
	public List<MemberEntity> findMembers();


	void addSomething(String something);

}
