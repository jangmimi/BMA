package com.ap4j.bma.controller.news;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@CrossOrigin("*")
public class newsController {

@RequestMapping("news")
public String newsPage(){

    return "news/newsPage";
}
}
