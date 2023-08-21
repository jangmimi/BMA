package com.ap4j.bma.controller.apt;

import com.ap4j.bma.service.apt.AptServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApiAptController {

    @Autowired
    AptServiceImpl aptServiceImpl;

    /** 아파트 실거래가 api 불러오기 */
    @GetMapping("csy")
    public String apt(Model model) {


        model.addAttribute("aptList",aptServiceImpl.getAptLists());

        return "/kakaoMap/aptMain";
    }
}
