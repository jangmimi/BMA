package com.ap4j.bma.controller.custmerCenter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes("loginMember")
@Controller
public class GuideController {


    //guide
    @GetMapping("/guide")
    public  String guideListForm() {

        return "customerCenter/guidePage";
    }

}
