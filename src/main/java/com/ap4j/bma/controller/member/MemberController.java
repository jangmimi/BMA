package com.ap4j.bma.controller.member;
// pjm - use m o p q
// controller -q | model -m | html -o | etc.. -p
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.entity.member.MemberEntity;
import com.ap4j.bma.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    private MemberService qMemberService;

    /** 로그인 페이지 매핑 */
    @RequestMapping(value="/qLoginForm")
    public String qLoginForm() {
        log.info("MemberController - qLoginForm() 실행");
        return "/userView/oLoginForm";
    }

    /** 카카오 로그인 실행 */
    @RequestMapping(value="/qLogin")
    public String qLogin(@RequestParam(value = "code", required = false) String code, Model model, HttpSession session) {
        log.info("MemberController - qLogin() 실행");
        log.info("####code : " + code);

        // 1번 인증코드 요청 전달
        String accessToken = qMemberService.getAccessToken(code);     // code로 토큰 받음
        log.info("accessToken : " + accessToken);

        // 2번 인증코드로 토큰 전달
        HashMap<String, Object> userInfo = qMemberService.getUserInfo(accessToken);   // 사용자 정보 받음
        log.info("login info : " + userInfo.toString());

        if(userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("userName", userInfo.get("nickname"));
            session.setAttribute("accessToken", accessToken);
        }
        model.addAttribute("userId", userInfo.get("email"));
        model.addAttribute("userName", userInfo.get("nickname"));
        return "/userView/oLoginForm";
    }

    /** 카카오 로그아웃 */
    @RequestMapping(value="/qLogout")
    public String qLogout(HttpSession session) {
        log.info("MemberController - qLogout() 실행");

        qMemberService.kakaoLogout((String)session.getAttribute("accessToken"));
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        session.removeAttribute("userName");

        log.info("로그아웃 완료되었습니다.");

        return "redirect:/qLoginForm";
    }

    /** 기본 로그인 */
    @PostMapping(value="/qLoginBasic")
    public String qBasicLogin(MemberDTO memberDTO, Model model) {
        log.info("MemberController - qBasicLogin() 실행");
        log.info("memberDTO : " + memberDTO);

        MemberEntity member = new MemberEntity();
        member.setName(memberDTO.getName());  // 아이디
        member.setEmail(memberDTO.getEmail());  // 비밀번호

        log.info("member name : " + member.getName());
        log.info("member email : " + member.getEmail());
        model.addAttribute("member",member);

        MemberEntity entity = memberDTO.toEntity();
        log.info("entity : " + entity.toString());

        qMemberService.joinBasic(member);
        return "redirect:/qLoginForm";
    }

    /** 기본 회원가입 페이지 매핑 */
    @RequestMapping(value="/qJoinForm")
    public String qJoinForm() {
        log.info("MemberController - qJoinForm() 실행");
        return "/userView/oJoinForm";
    }

    /** 기본 회원가입 */
    @PostMapping(value="/qJoinBasic")
    public String qJoinBasic(MemberDTO memberDTO, Model model) {
        log.info("MemberController - qJoinBasic() 실행");
        log.info("memberDTO : " + memberDTO.toString());

        MemberEntity member = new MemberEntity();
        member.setName(memberDTO.getName());  // 아이디
        member.setEmail(memberDTO.getEmail());  // 비밀번호

        log.info("member name : " + member.getName());
        log.info("member email : " + member.getEmail());
        model.addAttribute("member",member);

        MemberEntity entity = memberDTO.toEntity();
        log.info("entity : " + entity.toString());

        qMemberService.joinBasic(member);
        return "redirect:/qLoginForm";
    }

    /** 네이버 로그인 */
    @RequestMapping(value="/qLoginNaver")
    public String qLoginNaver() {
        log.info("MemberController - qLoginNaver() 실행");


        return "redirect:/qLoginForm";
    }

    @RequestMapping(value="/qLoginNaverCallback")
    public String qLoginNaverCallback() {
        log.info("MemberController - qLoginNaverCallback() 실행");

        return "redirect:/qLoginForm";
    }
}


//    // 네이버 로그인 테스트
//    @RequestMapping(value="/naverlogin2")
//    public ModelAndView naverlogin() {
//        log.info("pjm컨트롤러 - naverlogin() 실행");
//        ModelAndView mav = new ModelAndView();
//
//        mav.setViewName("apitest_pjm/pjm_naverLogin");
//        return mav;
//    }
//
//    @RequestMapping(value="/navercallback2")
//    public ModelAndView navercallback() {
//        log.info("pjm컨트롤러 - navercallback() 실행");
//        ModelAndView mav = new ModelAndView();
//
//        mav.setViewName("apitest_pjm/pjm_naverCallback");
//        return mav;
//    }