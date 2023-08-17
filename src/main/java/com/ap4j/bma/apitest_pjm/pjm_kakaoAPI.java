package com.ap4j.bma.apitest_pjm;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

@Slf4j
public class pjm_kakaoAPI {     // Service

    /** 토큰 얻기 */
    public String getAccessToken(String code) {
        String accessToken = "";
        String refreshToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=fae91fecfb7dbda2a80ae22881709a28");
            sb.append("&redirect_uri=http://localhost:8082/login");
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
        }catch (Exception e) {
            e.printStackTrace();
        }
        return accessToken;
    }

    /** 카카오 유저정보 얻기 */
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


        } catch (Exception e) {
            e.printStackTrace();
        }



        return userInfo;
    }

    /** 카카오 로그아웃 구현중 */
    public void kakaoLogout(String accessToken) {
        String reqURL = "http://kapi.kakao.com/v1/user/logout";
//        String reqURL = "\thttps://kauth.kakao.com/oauth/logout";
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
                result+=line;
            }
            log.info(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

// 로그아웃 작업중...
// 카카오 계정에서 로그아웃 하는 코드
// https://kauth.kakao.com/oauth/logout?client_id=fae91fecfb7dbda2a80ae22881709a28&logout_redirect_uri=http://localhost:8082/logout

// response body ={"id":2959937821,"connected_at":"2023-08-11T13:03:35Z",
// "properties":{"nickname":"박장미"},"kakao_account":{"profile_nickname_needs_agreement":false,"profile":{"nickname":"박장미"},
// "has_email":true,"email_needs_agreement":false,"is_email_valid":true,"is_email_verified":true,"email":"rose6012@hanmail.net"}}