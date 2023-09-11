package com.ap4j.bma.service.member;

import com.ap4j.bma.config.PasswordEncoderConfig;
import com.ap4j.bma.model.entity.customerCenter.QnAEntity;
import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.entity.member.MemberEntity;
import com.ap4j.bma.model.repository.MaemulRegEntityRepository;
import com.ap4j.bma.model.repository.MemberRepository;
import com.ap4j.bma.model.repository.QnARepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoderConfig pwdConfig;	// 비밀번호 암호화 객체 생성

	@Autowired
	private QnARepository qnARepository;

	@Autowired
	private MaemulRegEntityRepository maemulRegEntityRepository;

	/** 카카오 토큰 얻기 */
	public String getAccessToken(String code) {
		String clientID = "fae91fecfb7dbda2a80ae22881709a28";
		String redirectURL = "http://localhost:8081/member/qLogin";

		String accessToken = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");	// requestMethod를 POST로 설정
			conn.setDoOutput(true);			// POST 요청을 위해 기본값 false를 true로 변경

			// POST 요청에 필요로 요구하는 파라미터를 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

			// 파라미터 저장 후 전송
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=").append(clientID);
			sb.append("&redirect_uri=").append(redirectURL);
			sb.append("&code=").append(code);

			bw.write(sb.toString());
			bw.flush();

			int responseCode = conn.getResponseCode();
			log.info("response code = " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";
			while((line = br.readLine())!=null) {
				result += line;
			}
			log.info("response body = "+result);

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			accessToken = element.getAsJsonObject().get("access_token").getAsString();

			br.close();
			bw.close();
		} catch (Exception e) {
			log.info(e.toString());
		}
		return accessToken;
	}

	/** 카카오 유저 정보 얻기 */
	public HashMap<String, Object> getUserInfo(String accessToken) {
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		String reqUrl = "https://kapi.kakao.com/v2/user/me";
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
			int responseCode = conn.getResponseCode();
			log.info("responseCode = " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while((line = br.readLine()) != null) {
				result += line;
			}
			log.info("response body = " + result);  // 필요한 것만 뽑아 낼 수 있는지 확인하기

			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(result);

			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
			JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

			String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
			String name = kakaoAccount.getAsJsonObject().get("name").getAsString();
			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
			String phone_number = kakaoAccount.getAsJsonObject().get("phone_number").getAsString();
			String thumbnail_image = properties.getAsJsonObject().get("thumbnail_image").getAsString();

			userInfo.put("email", email);
			userInfo.put("name", name);
			userInfo.put("nickname", nickname);
			userInfo.put("phone_number", phone_number);
			userInfo.put("thumbnail_image", thumbnail_image);

		} catch (Exception e) {
			log.info(e.toString());
		}

		return userInfo;
	}

	/** 카카오 로그아웃 */
	public void kakaoLogout(String accessToken) {
		String reqURL = "https://kauth.kakao.com/oauth/logout";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
			int responseCode = conn.getResponseCode();
			log.info("responseCode = " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String result = "";
			String line = "";

			while((line = br.readLine()) != null) {
				result += line;
			}
			log.info(result);
		} catch (Exception e) {
			log.info(e.toString());
		}
	}

	/** 로그아웃 */
	public void logout(SessionStatus sessionStatus, HttpSession session) {
		sessionStatus.setComplete();
		session.invalidate();
	}

	/** 네이버 토큰 얻기 */
	@Override
	public String getAccessTokenNaver(String code) {
		String clientID = "KxGhFHZ7Xp74X_5IZ23h";
		String clientSecret = "yrJitK_hXC";
		String redirectURL = "http://localhost:8081/member/qLoginNaver";

		String accessToken = "";
		String reqURL = "https://nid.naver.com/oauth2.0/token";	// 인증 코드로 토큰 요청

		String state = "9999";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=").append(clientID);
			sb.append("&client_secret=").append(clientSecret);
			sb.append("&redirect_uri=").append(redirectURL);
			sb.append("&code=").append(code);
			sb.append("&state=").append(state);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			bw.write(sb.toString());
			bw.flush();

			int responseCode = conn.getResponseCode();
			log.info("response code = " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";
			while((line = br.readLine())!=null) {
				result += line;
			}
			log.info("response body = "+result);

			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			accessToken = element.getAsJsonObject().get("access_token").getAsString();

			br.close();
			bw.close();
		} catch (Exception e) {
			log.info(e.toString());
		}
		return accessToken;
	}

	/** 네이버 유저 정보 얻기 */
	public HashMap<String, Object> getUserInfoNaver(String accessToken) {
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		String reqUrl = "https://openapi.naver.com/v1/nid/me";
		try {
			URL url = new URL(reqUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
			int responseCode = conn.getResponseCode();
			log.info("responseCode = " + responseCode);

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = "";
			String result = "";

			while((line = br.readLine()) != null) {
				result += line;
			}
			log.info("response body = " + result);  // 필요한 것만 뽑아 낼 수 있는지 확인하기

			JsonParser parser = new JsonParser();
			JsonElement element =  parser.parse(result);

			JsonObject response = element.getAsJsonObject().get("response").getAsJsonObject();

			String email = response.getAsJsonObject().get("email").getAsString();
			String name = response.getAsJsonObject().get("name").getAsString();
			String nickname = response.getAsJsonObject().get("nickname").getAsString();
			String mobile = response.getAsJsonObject().get("mobile").getAsString();
			String profile_image = response.getAsJsonObject().get("profile_image").getAsString();

			userInfo.put("email", email);
			userInfo.put("name", name);
			userInfo.put("nickname", nickname);
			userInfo.put("mobile", mobile);
			userInfo.put("profile_image", profile_image);

		} catch (Exception e) {
			log.info(e.toString());
		}
		return userInfo;
	}

	/** 기본 회원가입 */
	@Transactional
	@Override
	public Long joinBasic(@ModelAttribute MemberDTO memberDTO) {
		// pwd는 암호화해서 가입 경로와 별도로 세팅
		if(memberDTO.getPwd() != null) {
			memberDTO.setPwd(pwdConfig.passwordEncoder().encode(memberDTO.getPwd()));
		}
		// 약관 동의 체크 여부에 따라 값 저장
		memberDTO.setChoice1(Boolean.TRUE.equals(memberDTO.getChoice1()));
		memberDTO.setChoice2(Boolean.TRUE.equals(memberDTO.getChoice2()));

		// sns 가입 시 연락처 +82 - 제거하고 저장
		String cleanTel = memberDTO.getTel().replaceAll("[^0-9+]","");
		if (cleanTel.startsWith("+82")) {
			cleanTel = "0" + cleanTel.substring(3); // +82 제거 후 0 추가
		}
		memberDTO.setTel(cleanTel.trim());

		MemberEntity entity = memberDTO.toEntity();

		memberRepository.save(entity);
		return entity.getId();
	}
	/** 이메일로 멤버 조회 */
	@Override
	public Optional<MemberEntity> findByEmail(String email) {
		return memberRepository.findByEmail(email);
	}

	/** 중복회원(이메일) 체크 */
	@Override
	public boolean existsByEmail(String email) {
		return memberRepository.existsByEmail(email);
	}

	/** 중복닉네임 체크 */
	@Override
	public boolean existsByNickname(String nickname) {
		return memberRepository.existsByNickname(nickname);
	}

	/** 기본 로그인 */
	@Override
	public MemberDTO login(MemberDTO memberDTO) {
		Optional<MemberEntity> findMember = memberRepository.findByEmail(memberDTO.getEmail());
		if (findMember.isPresent()) {
			log.info("로그인 시도하는 email DB에 존재!");
			MemberEntity memberEntity = findMember.get();
			if(memberEntity.getMember_leave()) { log.info("탈퇴한 회원"); return null; }

			if(memberEntity.getRoot() == 2) {	// 카카오네이버는 pwd 체크 없이 로그인 진행
                return memberEntity.toDTO();

			} else {
				if(pwdConfig.passwordEncoder().matches(memberDTO.getPwd(),memberEntity.getPwd())) {
                    return memberEntity.toDTO();

				} else {
					log.info("id일치, pw 불일치합니다.");
					return null;
				}
			}
		} else {
			log.info("존재하지 않는 회원입니다.");
			return null;
		}
	}

	/** 회원 탈퇴 - member_leave : true 변경 */
	public boolean leaveMember(Long id, String password, SessionStatus sessionStatus, HttpSession session) {
		Optional<MemberEntity> leaveMember = Optional.ofNullable(findMemberById(id));
		if(leaveMember.isPresent()) {
			int root = leaveMember.get().getRoot();
			if(root != 1) {
				log.info("SNS계정은 즉시 회원 탈퇴 처리 됩니다.");
				MemberEntity member = leaveMember.get();
				member.setMember_leave(true);	// 탈퇴 여부 값 변경
				memberRepository.save(member);
				logout(sessionStatus, session);	// 탈퇴 후 로그아웃 처리

				return true;
			}
			String dbPwd = leaveMember.get().getPwd();

			if(pwdConfig.passwordEncoder().matches(password, dbPwd)) {
				log.info("비밀번호 일치! 회원 탈퇴 시도");
				MemberEntity member = leaveMember.get();
				member.setMember_leave(true);	// 탈퇴 여부 값 변경
				memberRepository.save(member);
				logout(sessionStatus, session);	// 탈퇴 후 로그아웃 처리
		
				return true;
			} else {
				log.info("비밀번호가 일치하지 않습니다. 탈퇴 실패");
				return false;
			}
		}
		return false;
	}

	/** 회원 한명 찾기 id 기준 */
	public MemberEntity findMemberById(Long id) {
		Optional<MemberEntity> findMember = memberRepository.findById(id);
		return findMember.orElse(null);
	}

	/** 회원 정보 수정 */
	@Transactional
	@Override
	public MemberEntity updateMember(Long id, MemberDTO memberDTO) {
		Optional<MemberEntity> member = memberRepository.findById(id);

		if(member.isPresent()) {
			MemberEntity memberEntity = member.get();

			// pwd는 암호화해서 가입 경로와 별도로 세팅
			if(memberDTO.getPwd() != null && !memberDTO.getPwd().isEmpty()) {
				memberDTO.setPwd(pwdConfig.passwordEncoder().encode(memberDTO.getPwd()));
			}
			memberDTO.setChoice1(Boolean.TRUE.equals(memberDTO.getChoice1()));	// 이 처리 안해주면 언체크시 null됨
			memberDTO.setChoice2(Boolean.TRUE.equals(memberDTO.getChoice2()));
			memberDTO.updateEntity(memberEntity);

			return memberRepository.save(memberEntity);

		} else {
			return null;
		}
	}
	
	/** email 찾기 */
	@Override
	public Optional<MemberEntity> findByNameAndTel(String name, String tel) {
		return memberRepository.findByNameAndTel(name, tel);
	}

	/** 내 QnA 목록 */
	@Override
	public Page<QnAEntity> qMyQnaList(String userEmail, int page, int pageSize) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<QnAEntity> qMyQnaList = qnARepository.findQnaByEmail(userEmail,pageable);
		return qMyQnaList;
	}

	/* 내 QnA 목록 카운트*/
	public Long qMyQnaListCount(String usarEmail){
		return qnARepository.findQnaByEmai0lCount(usarEmail);
	}

	/** 매물 목록 전체 조회 */
	public List<MaemulRegEntity> getAllList() {
		return  maemulRegEntityRepository.findAll();
	}

	/** 내가 등록한 매물 목록 조회 */
	public List<MaemulRegEntity> getListByNickname(String nickname) {
		return  maemulRegEntityRepository.findMaemulByMemberNickname(nickname);
	}

	/** 내가 등록한 매물 삭제 */
	@Transactional
	public int deleteMaemul(Integer id, String nickname) {
		return maemulRegEntityRepository.deleteMByIdAndNickname(id, nickname);
	}

	/** 매물 전체 개수 */
	public Long getAllCnt() {
		return maemulRegEntityRepository.count();
	}

	/** 매물 id기준 조회 */
	public MaemulRegEntity findMaemulById(Integer id) {
		Optional<MaemulRegEntity> findMaemul = maemulRegEntityRepository.findMaemulById(id);
		return findMaemul.orElse(null);
	}

	@Override
	public Page<MaemulRegEntity> getPageByNickname(String nickname, int page, int pageSize) {
		Pageable pageable = PageRequest.of(page - 1, pageSize);
		Page<MaemulRegEntity> mmpList = maemulRegEntityRepository.findMaemulByMemberNicknameMy(nickname,pageable);
		return mmpList;
	}
}