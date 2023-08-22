package com.ap4j.bma.controller.news;

import com.ap4j.bma.service.news.NewsApiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@Controller
//@CrossOrigin("*")
//public class newsController {
//
//@RequestMapping("news")
//public String newsPage(){
//
//    return "news/newsPage";
//}
//}
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

//    @RequestMapping("/news/newsPage")
//    public String search(@RequestParam("query") String searchQuery, Model model) {
//        String searchResult = newsApiService.searchBlog(searchQuery);
//        model.addAttribute("searchResult", searchResult);
//        log.info(searchResult);
//        return "news/newsPage";
//    }

    @RequestMapping("/news/newsPage")
    public String search(@RequestParam("query") String searchQuery, Model model) {
        String searchResult = newsApiService.searchBlog(searchQuery);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(searchResult);
            JsonNode items = jsonNode.get("items");

            List<Map<String, String>> newsList = new ArrayList<>();
            for (JsonNode item : items) {
                String title = item.get("title").asText();
                String link = item.get("link").asText();

                Map<String, String> newsItem = new HashMap<>();
                newsItem.put("title", removeHTMLTags(title));
                newsItem.put("link", link);

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
        String regex = "/(<([^>]+)>)|&quot;/ig";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }
}