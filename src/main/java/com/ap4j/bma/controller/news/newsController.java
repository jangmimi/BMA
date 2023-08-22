package com.ap4j.bma.controller.news;

import com.ap4j.bma.service.news.NewsApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Slf4j
@Controller
public class newsController {

    private final NewsApiService newsApiService;

    public newsController(NewsApiService newsApiService) {
        this.newsApiService = newsApiService;
    }

    @RequestMapping("news")
    public String newsPage(){

        return "news/newsPage";
    }
//
//// 페이지네이션 작업중
//    @RequestMapping("news/newsPage")
//    public String search(
//            @RequestParam("query") String searchQuery,
//            @RequestParam(value = "page", defaultValue = "1") int page,
//            Model model
//    ) {
//        int resultsPerPage = 10; // 원하는 페이지당 결과 수
//
//        String searchResult = newsApiService.searchNews(searchQuery);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            JsonNode jsonNode = objectMapper.readTree(searchResult);
//            JsonNode items = jsonNode.get("items");
//
//            List<Map<String, String>> newsList = new ArrayList<>();
//            for (JsonNode item : items) {
//                String title = item.get("title").asText();
//                String link = item.get("link").asText();
//                String description = item.get("description").asText();
//
//                Map<String, String> newsItem = new HashMap<>();
//                newsItem.put("title", removeHTMLTags(title));
//                newsItem.put("link", link);
//                newsItem.put("description", removeHTMLTags(description));
//                newsList.add(newsItem);
//            }
//
//            int totalResults = newsList.size();
//            int totalPages = (int) Math.ceil((double) totalResults / resultsPerPage);
//
//            int startIndex = (page - 1) * resultsPerPage;
//            int endIndex = Math.min(startIndex + resultsPerPage, totalResults);
//
//            List<Map<String, String>> paginatedNewsList = newsList.subList(startIndex, endIndex);
//
//            model.addAttribute("newsList", paginatedNewsList);
//            model.addAttribute("currentPage", page);
//            model.addAttribute("totalPages", totalPages);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            // Handle exception if necessary
//        }
//
//        log.info(searchResult);
//        return "news/newsPage";
//    }


    @RequestMapping("/news/newsPage")
    public String search(@RequestParam("query") String searchQuery, Model model) {
        String searchResult = newsApiService.searchNews(searchQuery);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(searchResult);
            JsonNode items = jsonNode.get("items");

            List<Map<String, String>> newsList = new ArrayList<>();
            for (JsonNode item : items) {
                String title = item.get("title").asText();
                String link = item.get("link").asText();
                String description = item.get("description").asText();

                Map<String, String> newsItem = new HashMap<>();
                newsItem.put("title", removeHTMLTags(title));
                newsItem.put("link", link);
                newsItem.put("description", removeHTMLTags(description));
                newsList.add(newsItem);
            }

            model.addAttribute("newsList", newsList);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception if necessary
        }

        log.info(searchResult);
        return "news/newsPage";
    }
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