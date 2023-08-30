package com.ap4j.bma.service.member;

import com.ap4j.bma.config.PasswordEncoderConfig;
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.entity.member.MemberEntity;
import com.ap4j.bma.model.repository.MemberRepository;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoderConfig pwdConfig;

	/** 카카오 토큰 얻기 */
	public String getAccessToken(String code) {
		String accessToken = "";
		String refreshToken = "";
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
			sb.append("&client_id=fae91fecfb7dbda2a80ae22881709a28");
			sb.append("&redirect_uri=http://localhost:8081/member/qLogin");
			sb.append("&code="+code);

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
			refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

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
//			String profile_image_url = properties.getAsJsonObject().get("thumbnail_image_url").getAsString();

			userInfo.put("email", email);
			userInfo.put("name", name);
			userInfo.put("nickname", nickname);
			userInfo.put("phone_number", phone_number);
//			userInfo.put("thumbnail_image_url", profile_image_url);

			Optional<MemberEntity> tmp = memberRepository.findByEmail(email);
			log.info("test tmp (email기준 회원정보있나~?) : " + tmp);
			log.info("tmp : " + tmp.toString());

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

	/** 네이버 토큰 얻기 */
	@Override
	public String getAccessTokenNaver(String code) {
		String accessToken = "";
		String refreshToken = "";
		String reqURL = "https://nid.naver.com/oauth2.0/authorize";
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString(32);

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

			StringBuilder sb = new StringBuilder();
			sb.append("response_type=code");
			sb.append("&client_id=KxGhFHZ7Xp74X_5IZ23h");
			sb.append("&redirect_uri=http://localhost:8081/memeber/qLoginNaverCallback");
			sb.append("&state="+state);

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
			refreshToken = element.getAsJsonObject().get("refresh_token").getAsString();

			br.close();
			bw.close();
		} catch (Exception e) {
			log.info(e.toString());
		}
		return accessToken;
	}

	/** 기본 회원가입 */
	@Transactional
	@Override
	public Long joinBasic(MemberEntity pMember) {
		log.info("서비스 joinBasic() 실행");
		memberRepository.save(pMember);
		return pMember.getId();			// @GeneratedValue 로 id는 자동으로 값 저장
	}

	/** 중복회원 검증 */
	@Override
	public boolean existsByEmail(String email) {
		log.info("서비스 existsByEmail() 실행");
		return memberRepository.existsByEmail(email);
	}

	/** 회원전체 조회 */
	@Override
	public List<MemberEntity> findMembers() {
		log.info("서비스 findMember() 실행");
		return memberRepository.findAll();
	}

	/** 기본 로그인 */
	@Override
	public MemberDTO login(MemberDTO memberDTO) {
		log.info("서비스 login() 실행");
		Optional<MemberEntity> findMember = memberRepository.findByEmail(memberDTO.getEmail());
		if (findMember.isPresent()) {
			log.info("로그인 시도하는 email DB에 존재!");
			MemberEntity memberEntity = findMember.get();
			if(pwdConfig.passwordEncoder().matches(memberDTO.getPwd(),memberEntity.getPwd())) {
				log.info("id pw 모두 일치! 로그인 성공!");
				MemberDTO dto = memberEntity.toDTO();
				log.info("entity를 toDTO : " +  dto);
				return dto;
			} else {
				log.info("id일치, pw 불일치합니다.");
			}
		} else {
			log.info("존재하지 않는 회원입니다.");
		}
		return null;
	}

	/** 회원 탈퇴 */
	public void deleteMemberById(Long id) {
		memberRepository.deleteById(id);
	}

	/** 회원 한명 찾기 */
	public MemberEntity getMemberOne(String email) {
		log.info("서비스 getMemberOne() 실행");
		Optional<MemberEntity> findMember = memberRepository.findByEmail(email);
		return findMember.orElse(null);
	}

	/** 회원 정보 수정 */
	@Transactional
	@Override
	public MemberEntity updateMember(Long id, MemberDTO memberDTO) {
		log.info("서비스 updateMember() 실행");
		log.info("updatedMember : " + memberDTO);
		log.info("비밀번호 변경 : " + memberDTO.getPwd());
		log.info("setChoice1 : " + memberDTO.getChoice1());
		log.info("setChoice2 : " + memberDTO.getChoice2());

		if(memberDTO.getPwd() != null) {	// 비밀번호 변경 값이 있을 경우
			memberDTO.setPwd(pwdConfig.passwordEncoder().encode(memberDTO.getPwd()));
		}
		if(memberDTO.getChoice1() != null) {
			memberDTO.setChoice1(memberDTO.getChoice1());
		}
		if(memberDTO.getChoice2() != null) {
			memberDTO.setChoice2(memberDTO.getChoice2());
		}

		Optional<MemberEntity> member = memberRepository.findById(id);
		log.info("조회된 member : " + member);

		if(member.isPresent()) {
			MemberEntity memberEntity = member.get();
			memberDTO.updateEntity(memberEntity);

			log.info("수정된 정보 : " + memberEntity);
			return memberRepository.save(memberEntity);
		} else {
			return null;
		}
	}

	/** email 찾기(이름 연락처로) */
	@Override
	public Optional<MemberEntity> findByNameAndTel(String name, String tel) {
		return memberRepository.findByNameAndTel(name, tel);
	}

	/** pwd 찾기(이메일 연락처로) */
	@Override
	public Optional<MemberEntity> findByEmailAndTel(String email, String tel) {
		return memberRepository.findByEmailAndTel(email, tel);
	}

	/** 회원가입 유효성 검사 */
//	@Override
//	public Map<String, String> validateHandler(Errors errors) {
//		Map<String, String> validatorResult = new HashMap<>();
//
//		for(FieldError error : errors.getFieldErrors()) {
//			String validKeyName = String .format("valid_%s", error.getField());
//			validatorResult.put(validKeyName, error.getDefaultMessage());
//		}
//		return validatorResult;
//	}

	// 아래는 예시 코드입니다.
//	@Override
//	@Transactional // 트랜잭션 처리하기
//	public void addSomething(String something) {
//	}
}
