package com.ap4j.bma.controller.introduce;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@Slf4j
public class introduceController {

    /* 전문가 소개 페이지*/
    @RequestMapping("introduce")
    public String introduce(){
        return "introduce/proIntroduce";
    }

}

