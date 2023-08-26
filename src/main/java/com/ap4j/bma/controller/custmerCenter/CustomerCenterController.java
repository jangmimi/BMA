package com.ap4j.bma.controller.custmerCenter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerCenterController {


    //guide
    @GetMapping("/guide")
    public  String guideListForm() {

        return "customerCenter/guidePage";
    }


    //QNA
    @GetMapping("/qna")
    public  String QnAWriteForm(){

        return "customerCenter/QnABoard/QnAWrite";
    }
}
