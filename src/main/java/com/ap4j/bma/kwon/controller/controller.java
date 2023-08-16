package com.ap4j.bma.kwon.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class controller {


    @GetMapping("kwon/test")
    public String kwonTest(Model model){
        log.info("테스트 확인");


        return "kwon/test";
    }
}
