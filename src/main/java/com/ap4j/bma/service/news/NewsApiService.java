package com.ap4j.bma.service.news;


import com.ap4j.bma.api.news.News;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class NewsApiService {

    private final String clientId = "JeoZb70w2v35nvvPqzLN"; // 애플리케이션 클라이언트 아이디
    private final String clientSecret = "e6EN3rbnqs"; // 애플리케이션 클라이언트 시크릿

//    private final String clientId = "lXF9D0J81ibDjCsxzkKE"; // 애플리케이션 클라이언트 아이디
//    private final String clientSecret = "rodfxhs30y"; // 애플리케이션 클라이언트 시크릿

//    public String searchBlog(String query) {
//        String apiUrl = "https://openapi.naver.com/v1/search/news?query=" + query;
//        Map<String, String> requestHeaders = new HashMap<>();
//        requestHeaders.put("X-Naver-Client-Id", clientId);
//        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
//        log.info(News.get(apiUrl, requestHeaders));
//        return News.get(apiUrl, requestHeaders);
//    }

    public String searchBlog(String query) {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
            String apiUrl = "https://openapi.naver.com/v1/search/news?query=" + encodedQuery;
            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("X-Naver-Client-Id", clientId);
            requestHeaders.put("X-Naver-Client-Secret", clientSecret);
            log.info(News.get(apiUrl, requestHeaders));
            return News.get(apiUrl, requestHeaders);
        } catch (UnsupportedEncodingException e) {
            log.error("URL encoding error: " + e.getMessage());
            // 에러 핸들링을 원하는 대로 수행
            return ""; // 예시로 빈 문자열 반환
        }
    }
}