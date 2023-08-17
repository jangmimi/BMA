package com.ap4j.bma.apitest_pjm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Slf4j
@RestController
public class pjm_Controller2 {

//    @Autowired
//    private pjm_Repository pjm_repository;

    /* 로그인 테스트 */
    pjm_kakaoAPI kakaoApi = new pjm_kakaoAPI();

    /* 로그인 페이지 매핑 */
    @RequestMapping(value="/loginForm")
    public ModelAndView loginForm() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/apitest_pjm/pjm_home");
        return mav;
    }
    
    @RequestMapping(value="/login")
    public ModelAndView login(@RequestParam("code") String code, HttpSession session) {
        log.info("pjm컨트롤러 - login() 실행");
        ModelAndView mav = new ModelAndView();
        // 1번 인증코드 요청 전달
        String accessToken = kakaoApi.getAccessToken(code);     // code로 토큰 받음
        log.info("accessToken : " + accessToken);
        // 2번 인증코드로 토큰 전달
        HashMap<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);   // 사용자 정보 받음
        log.info("login info : " + userInfo.toString());

        if(userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("userName", userInfo.get("nickname"));
            session.setAttribute("accessToken", accessToken);
        }
        mav.addObject("userId", userInfo.get("email"));
        mav.addObject("userName", userInfo.get("nickname"));
        mav.setViewName("apitest_pjm/pjm_home");
        return mav;
    }

    @RequestMapping(value="/logout")
    public ModelAndView logout(HttpSession session) {
        log.info("pjm컨트롤러 - logout() 실행");
        ModelAndView mav = new ModelAndView();

        kakaoApi.kakaoLogout((String)session.getAttribute("accessToken"));

        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        session.removeAttribute("userName");
        mav.setViewName("redirect:/loginForm");

        log.info("로그아웃 완료되었습니다.");
        return mav;
    }



    // 네이버 로그인 테스트
    @RequestMapping(value="/naverlogin")
    public ModelAndView naverlogin() {
        log.info("pjm컨트롤러 - naverlogin() 실행");
        ModelAndView mav = new ModelAndView();

        mav.setViewName("apitest_pjm/pjm_naverLogin");
        return mav;
    }

    @RequestMapping(value="/navercallback")
    public ModelAndView navercallback() {
        log.info("pjm컨트롤러 - navercallback() 실행");
        ModelAndView mav = new ModelAndView();

        mav.setViewName("apitest_pjm/pjm_naverCallback");
        return mav;
    }

}

