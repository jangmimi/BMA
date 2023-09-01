package com.ap4j.bma.controller.news;

import com.ap4j.bma.service.news.NewsApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SessionAttributes("userEmail")
@Slf4j
@Controller
public class newsController {

    private final NewsApiService newsApiService;

    public newsController(NewsApiService newsApiService) {
        this.newsApiService = newsApiService;
    }

    @RequestMapping("news")
    public String newsPage(Model model) {
        String defaultSearchQuery = "부동산"; // 기본 검색어 설정

        return search(defaultSearchQuery, 1, 1, model); // 처음 페이지에 기본 검색어를 이용하여 결과 출력
    }


@RequestMapping("news/newsPage")
public String search(
        @RequestParam("query") String searchQuery,
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "start", defaultValue = "1") int start,
        Model model) {
    String searchResult = newsApiService.searchNews(searchQuery.replaceAll(" ",""), start);
    log.info(searchQuery);

    DateTimeFormatter dateformat = DateTimeFormatter.ofPattern("yyyy.MM.dd a hh:mm");

    ObjectMapper objectMapper = new ObjectMapper();
    try {
        JsonNode jsonNode = objectMapper.readTree(searchResult);
        JsonNode items = jsonNode.get("items");

        log.info(String.valueOf(items));
        List<Map<String, String>> newsList = new ArrayList<>();
        String searchKeyword = null;
        if (items != null) {
            for (JsonNode item : items) {
                String title = item.get("title").asText();
                String link = item.get("link").asText();
                String description = item.get("description").asText();
                String pubDate = item.get("pubDate").asText();

                LocalDateTime dateTime = LocalDateTime.parse(pubDate, DateTimeFormatter.RFC_1123_DATE_TIME);
                String formattedPubDate = dateTime.format(dateformat);

                Pattern searchPattern = Pattern.compile("<b>(.*?)</b>");
                Matcher matcher = searchPattern.matcher(description);

                searchKeyword = null;
                if (matcher.find()) {
                    searchKeyword = matcher.group(1); // 정규식 그룹에서 추출
                }


                Map<String, String> newsItem = new HashMap<>();
                newsItem.put("title", removeHTMLTags(title));
                newsItem.put("link", link);
                newsItem.put("description", removeHTMLTags(description));
                newsItem.put("pubDate", formattedPubDate);
                newsItem.put("searchKeyword", searchKeyword);

                newsList.add(newsItem);
            }
        } else {
            model.addAttribute("itemsEmpty", true);
        }
        int totalResults = jsonNode.get("total").asInt(); // 뉴스의 전체 개수

        int itemsPerPage = 10; // 페이지당 뉴스 항목 수
        int startIndex = (page - 1) * itemsPerPage;

        int newStartIndex = start + 10 * (page - 1); // start 값을 조정


        int totalPages = (int) Math.ceil((double) totalResults / itemsPerPage);

        // 네이버 검색 api는 start 파라미터를 최대 1000까지 받을 수 있기 때문에 전체 개수와 max 페이지 설정
        if (totalResults > 1000) {
            totalResults = 1000;
            totalPages = 100;
        }

        int endIndex = Math.min(startIndex + itemsPerPage, newsList.size());

        // 마지막 페이지에서의 endIndex 조정
        if (page == totalPages) {
            endIndex = newsList.size();
        }

        List<Map<String, String>> paginatedNewsList = newsList.subList(0, endIndex);

        model.addAttribute("newsList", paginatedNewsList);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("searchQuery", searchQuery.replaceAll(" ",""));

        model.addAttribute("start", newStartIndex);

        model.addAttribute("total", totalResults);

        model.addAttribute("itemsPerPage", itemsPerPage);

        if (searchKeyword != null) {
            model.addAttribute("searchKeyword", searchKeyword.replaceAll(" ","")); // 정확도가 높은 검색어
        } else {
            model.addAttribute("searchKeyword", searchKeyword);
        }

        log.info(searchKeyword);
    } catch (IOException e) {
        e.printStackTrace();
    }

    log.info(searchResult);
    return "news/newsPage";
}

    // 받아온 api 데이터에서 html 태그들 제거 
    public static String removeHTMLTags(String input) {

        String cleanText = input
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&quot;", "\"")
                .replace("&apos;", "'")
                .replace("&amp;", "&")
                .replaceAll("<b>", "")
                .replaceAll("</b>", "");
        return cleanText;
    }
}