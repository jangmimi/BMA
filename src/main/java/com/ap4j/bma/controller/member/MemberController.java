package com.ap4j.bma.controller.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Controller
public class MemberController {

    // 로그인
    
    /** 로그인 페이지 매핑 */
    @RequestMapping(value="/loginForm")
    public ModelAndView loginForm() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/userView/loginForm");
        return mav;
    }
}
