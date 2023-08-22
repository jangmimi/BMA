package com.ap4j.bma.controller.apartment;

import com.ap4j.bma.service.apartment.ApartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApartmentController {

    @Autowired
    ApartmentServiceImpl aptServiceImpl;

    /** 아파트 실거래가 api 불러오기 */
    @GetMapping("csy")
    public String apartment(Model model) {


        model.addAttribute("aptList",aptServiceImpl.getAptLists());

        return "/kakaoMap/aptMain";
    }
}
