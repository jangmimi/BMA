package com.ap4j.bma.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
@Slf4j
@Controller
@SessionAttributes("loginMember")
public class HomeController {

    @RequestMapping("/")
    public String mainPage(HttpSession session){
        return "mainPage/mainPage";
    }

}
