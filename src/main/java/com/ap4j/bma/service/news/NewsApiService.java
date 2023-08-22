package com.ap4j.bma.service.news;


import com.ap4j.bma.api.news.News;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class NewsApiService {

//    private final String clientId = "JeoZb70w2v35nvvPqzLN"; // 애플리케이션 클라이언트 아이디
//    private final String clientSecret = "e6EN3rbnqs"; // 애플리케이션 클라이언트 시크릿

    private final String clientId = "lXF9D0J81ibDjCsxzkKE"; // 애플리케이션 클라이언트 아이디
    private final String clientSecret = "rodfxhs30y"; // 애플리케이션 클라이언트 시크릿

    public String searchBlog(String query) {
        String apiUrl = "https://openapi.naver.com/v1/search/news?query=" + query;
        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        log.info(News.get(apiUrl, requestHeaders));
        return News.get(apiUrl, requestHeaders);
    }
}