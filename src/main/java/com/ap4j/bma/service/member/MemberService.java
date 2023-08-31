package com.ap4j.bma.service.member;

import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.entity.member.MemberEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface MemberService {

	public String getAccessToken(String code);

	public HashMap<String, Object> getUserInfo(String accessToken);	// HashMap -> MemberDTO로 변경 예정
//	public MemberDTO getUserInfo2(String accessToken);

	public void kakaoLogout(String accessToken);

	public String getAccessTokenNaver(String code);

	public Long joinBasic(MemberEntity pMember);

	public boolean existsByEmail(String email);

	public List<MemberEntity> findMembers();

	public MemberDTO login(MemberDTO memberDTO);

	public MemberEntity findMemberById(Long id);

	public MemberEntity updateMember(Long id, MemberDTO memberDTO);

	public boolean leaveMember(Long id);

	public Optional<MemberEntity> findByNameAndTel(String name, String tel);

	public Optional<MemberEntity> findByEmailAndTel(String email, String tel);
}

//    Map<String, String> validateHandler(Errors errors);
//	public void validateDuplicateMember(MemberEntity pMember);
