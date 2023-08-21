package com.ap4j.bma.service.member;

import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.entity.member.MemberEntity;

import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;

public interface MemberService {

	/** 카카오 토큰 얻기 */
	public String getAccessToken(String code);

	/** 카카오 유저 정보 얻기 */
	public HashMap<String, Object> getUserInfo(String accessToken);	// HashMap -> MemberDTO로 변경 예정
//	public MemberDTO getUserInfo2(String accessToken);

	/** 카카오 로그아웃 */
	public void kakaoLogout(String accessToken);

	/** 네이버 토큰 얻기 */
	public String getAccessTokenNaver(String code);

	/** 네이버 로그인 */

	/** 기본 회원가입 */
	public Long joinBasic(MemberEntity pMember);

	/** 중복회원 검증 */
	public boolean existsByEmail(String email);
//	public void validateDuplicateMember(MemberEntity pMember);

	/** 회원전체 조회 */
	public List<MemberEntity> findMembers();

	/** 기본 로그인 */
	public MemberEntity login(String loginEmail);

}
