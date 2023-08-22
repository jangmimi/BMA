package com.ap4j.bma.controller.news;

import com.ap4j.bma.service.news.NewsApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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

    @RequestMapping("/news/newsPage")
    public String search(@RequestParam("query") String searchQuery, Model model) {
        String searchResult = newsApiService.searchBlog(searchQuery);
        model.addAttribute("searchResult", searchResult);
        log.info(searchResult);
        return "news/newsPage";
    }
}