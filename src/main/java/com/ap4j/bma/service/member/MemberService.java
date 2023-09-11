package com.ap4j.bma.service.member;

import com.ap4j.bma.model.entity.customerCenter.QnAEntity;
import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.entity.member.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface MemberService {

	public String getAccessToken(String code);

	public HashMap<String, Object> getUserInfo(String accessToken);

	public void logout(SessionStatus sessionStatus, HttpSession session);

	public String getAccessTokenNaver(String code);

	public HashMap<String, Object> getUserInfoNaver(String accessToken);

	public Long joinBasic(@ModelAttribute MemberDTO memberDTO);

	public boolean existsByEmail(String email);

	public Optional<MemberEntity> findByEmail(String email);

	public boolean existsByNickname(String nickname);

	public MemberDTO login(MemberDTO memberDTO);

	public MemberEntity findMemberById(Long id);

	public MemberEntity updateMember(Long id, MemberDTO memberDTO);

	public boolean leaveMember(Long id, String password, SessionStatus sessionStatus, HttpSession session);

	public Optional<MemberEntity> findByNameAndTel(String name, String tel);

	// 매물 관련 qna - 김재환수정
	public Page<QnAEntity> qMyQnaList(String userEmail, int page, int pageSize);
	// qna 카운트 - 김재환
	public Long qMyQnaListCount(String usarEmail);
	public List<MaemulRegEntity> getListByNickname(String nickname);
	public MaemulRegEntity findMaemulById(Integer id);
	public int deleteMaemul(Integer id, String nickname);

}
