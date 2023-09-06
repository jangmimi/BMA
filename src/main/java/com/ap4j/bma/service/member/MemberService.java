package com.ap4j.bma.service.member;

import com.ap4j.bma.model.entity.customerCenter.QnAEntity;
import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.entity.member.MemberEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface MemberService {

	public String getAccessToken(String code);

	public HashMap<String, Object> getUserInfo(String accessToken);	// HashMap -> MemberDTO로 변경 예정

	public void kakaoLogout(String accessToken);

	public void logout(SessionStatus sessionStatus, HttpSession session);

	public String getAccessTokenNaver(String code);

	public HashMap<String, Object> getUserInfoNaver(String accessToken);

	public Long joinBasic(@ModelAttribute MemberDTO memberDTO);

	public boolean existsByEmail(String email);

	public boolean existsByNickname(String nickname);

	public List<MemberEntity> findMembers();

	public MemberDTO login(MemberDTO memberDTO);

	public MemberEntity findMemberById(Long id);

	public MemberEntity updateMember(Long id, MemberDTO memberDTO);

	public boolean leaveMember(Long id, String password, SessionStatus sessionStatus, HttpSession session);

	public Optional<MemberEntity> findByNameAndTel(String name, String tel);

	public Optional<MemberEntity> findByEmailAndTel(String email, String tel);

	// 매물 관련 qna
	public List<QnAEntity> qMyQnaList();
	public long qMyQnaCnt(String userEmail);
//	public long qMyQnaCnt();
	public List<MaemulRegEntity> getAllList();
	public Long getAllCnt();
}

//    Map<String, String> validateHandler(Errors errors);
//	public void validateDuplicateMember(MemberEntity pMember);
