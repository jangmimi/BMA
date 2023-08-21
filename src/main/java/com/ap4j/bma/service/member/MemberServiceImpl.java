package com.ap4j.bma.service.member;

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
			sb.append("&redirect_uri=http://localhost:8081/qLogin");
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
//			e.printStackTrace();
		}
		return accessToken;
	}
//	public MemberDTO getUserInfo2(String accessToken) {
//		MemberDTO dto = new MemberDTO();
//		String reqUrl = "https://kapi.kakao.com/v2/user/me";
//		try {
//			URL url = new URL(reqUrl);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Authorization", "Bearer " + accessToken);
//			int responseCode = conn.getResponseCode();
//			log.info("responseCode = " + responseCode);
//
//			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//
//			String line = "";
//			String result = "";
//
//			while((line = br.readLine()) != null) {
//				result += line;
//			}
//			log.info("response body = " + result);  // 필요한 것만 뽑아 낼 수 있는지 확인하기
//
//			JsonParser parser = new JsonParser();
//			JsonElement element =  parser.parse(result);
//
//			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
//			JsonObject kakaoAccount = element.getAsJsonObject().get("kakao_account").getAsJsonObject();
//
//			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
//			String email = kakaoAccount.getAsJsonObject().get("email").getAsString();
//
//			dto.setName("nickname");
//			dto.setEmail("email");
//
//			MemberEntity tmp = memberRepository.findByEmail(email);
//
//			log.info("test tmp (email기준 회원정보있나~?) : " + tmp);
//
////			@Override
////			public Long joinBasic(MemberEntity pMember) {
////				memberRepository.save(pMember);
////				return pMember.getId();
////			}
//
//		} catch (Exception e) {
//			log.info(e.toString());
////			e.printStackTrace();
//		}
//
//		return dto;
//	}

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

			String nickname = properties.getAsJsonObject().get("nickname").getAsString();
			String email = kakaoAccount.getAsJsonObject().get("email").getAsString();

			userInfo.put("nickname", nickname);
			userInfo.put("email", email);

			MemberEntity tmp = memberRepository.findByEmail(email);
			log.info("test tmp (email기준 회원정보있나~?) : " + tmp);
			tmp.setEmail(email);
			tmp.setName(nickname);
			log.info("tmp : " + tmp.toString());

//			@Override
//			public Long joinBasic(MemberEntity pMember) {
//				memberRepository.save(pMember);
//				return pMember.getId();
//			}

		} catch (Exception e) {
			log.info(e.toString());
//			e.printStackTrace();
		}

		return userInfo;
	}

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
			sb.append("&redirect_uri=http://localhost:8081/qLoginNaverCallback");
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
//			e.printStackTrace();
		}
		return accessToken;
	}

	@Override
	public Long joinBasic(MemberEntity pMember) {
		log.info("서비스 joinBasic() 실행");
//		validateDuplicateMember(pMember);    // 중복 회원 검증

		memberRepository.save(pMember);
		return pMember.getIdx();
	}

	@Override
	public boolean existsByEmail(String email) {
		log.info("서비스 existsByEmail() 실행");
		return memberRepository.existsByEmail(email);
	}

//	public Long joinBasic(MemberEntity pMember) {
////		validateDuplicateMember(pMember);    // 중복 회원 검증
//
//		memberRepository.save(pMember);
//		return pMember.getId();
//	}

	@Override
	public List<MemberEntity> findMembers() {
		log.info("서비스 findMember() 실행");
		return memberRepository.findAll();
	}

	@Override
	public MemberEntity login(String loginEmail) {
		log.info("서비스 loginByEmail() 실행");
		MemberEntity findMember = memberRepository.findByEmail(loginEmail);


        return memberRepository.findByEmail(loginEmail);
    }


	// 아래는 예시 코드입니다.
//	@Override
//	@Transactional // 트랜잭션 처리하기
//	public void addSomething(String something) {
//	}
}

// 카카오 로그아웃 작업중 (작업 완료 8/20)

// response body ={"id":2959937821,"connected_at":"2023-08-11T13:03:35Z",
// "properties":{"nickname":"박장미"},"kakao_account":{"profile_nickname_needs_agreement":false,"profile":{"nickname":"박장미"},
// "has_email":true,"email_needs_agreement":false,"is_email_valid":true,"is_email_verified":true,"email":"rose6012@hanmail.net"}}