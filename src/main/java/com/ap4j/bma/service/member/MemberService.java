package com.ap4j.bma.service.member;

import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.entity.member.MemberEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MemberService {

	public String getAccessToken(String code);

	public HashMap<String, Object> getUserInfo(String accessToken);	// HashMap -> MemberDTO로 변경 예정
//	public MemberDTO getUserInfo2(String accessToken);

	public void kakaoLogout(String accessToken);

	public String getAccessTokenNaver(String code);

	public Long joinBasic(MemberEntity pMember);

	public boolean existsByEmail(String email);
//	public void validateDuplicateMember(MemberEntity pMember);

	public List<MemberEntity> findMembers();

	public MemberDTO login(MemberDTO memberDTO);
//	public MemberEntity login(String loginEmail);

    Map<String, String> validateHandler(Errors errors);

//	public Long updateMember(final Long idx, final MemberDTO memberDTO);

}
