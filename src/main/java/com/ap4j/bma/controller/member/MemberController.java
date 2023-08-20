package com.ap4j.bma.controller.member;
// pjm - use m o p q
// controller -q | model -m | html -o | etc.. -p
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.entity.member.MemberEntity;
import com.ap4j.bma.service.member.MemberService;
import com.ap4j.bma.service.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
public class MemberController {

    @Autowired
    private MemberService qMemberServiceImpl;

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
        String accessToken = qMemberServiceImpl.getAccessToken(code);     // code로 토큰 받음
        log.info("accessToken : " + accessToken);

        // 2번 인증코드로 토큰 전달
//        MemberDTO userInfo = qMemberServiceImpl.getUserInfo2(accessToken);   // 사용자 정보 받음
        HashMap<String, Object> userInfo = qMemberServiceImpl.getUserInfo(accessToken);   // 사용자 정보 받음
        log.info("login info : " + userInfo.toString());
        // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
        log.info("login userUniqueId : " + userInfo.get("id"));
        log.info("login userId(email) : " + userInfo.get("email"));
        log.info("login userName : " + userInfo.get("nickname"));

        if(userInfo.get("email") != null) {
            session.setAttribute("userId", userInfo.get("email"));
            session.setAttribute("userName", userInfo.get("nickname"));
            session.setAttribute("accessToken", accessToken);
        }
        model.addAttribute("userId", userInfo.get("email"));
        model.addAttribute("userName", userInfo.get("nickname"));
//        if(userInfo.getEmail() != null) {
//            session.setAttribute("userId", userInfo.getEmail());
//            session.setAttribute("userName", userInfo.getName());
//            session.setAttribute("accessToken", accessToken);
//        }
//        model.addAttribute("userId", userInfo.getEmail());
//        model.addAttribute("userName", userInfo.getName());

        MemberEntity member = new MemberEntity();
        member.setName((String) userInfo.get("nickname"));  // 아이디
        member.setEmail((String) userInfo.get("email"));  // 비밀번호
        log.info(member.toString());

        qMemberServiceImpl.joinBasic(member);   // 카카오정보로 회원가입 실행

        return "/userView/oLoginForm";
    }

    /** 카카오 로그아웃 */
    @RequestMapping(value="/qLogout")
    public String qLogout(HttpSession session) {
        log.info("MemberController - qLogout() 실행");

        qMemberServiceImpl.kakaoLogout((String)session.getAttribute("accessToken"));
        session.removeAttribute("accessToken");
        session.removeAttribute("userId");
        session.removeAttribute("userName");

        log.info("로그아웃 완료되었습니다.");

        return "redirect:/qLoginForm";
    }

    /** 기본 로그인 */
    @PostMapping(value="/qLoginBasic")
    public String qBasicLogin(@ModelAttribute MemberDTO memberDTO, Model model, HttpSession session) {
        log.info("MemberController - qBasicLogin() 실행");
        log.info("memberDTO : " + memberDTO);

        MemberEntity member = new MemberEntity();
        member.setEmail(memberDTO.getEmail());
        member.setPwd(memberDTO.getPwd());
        log.info(member.toString());

        MemberEntity result = qMemberServiceImpl.loginByEmail(member.getEmail());

//        if(qMemberServiceImpl.loginByEmail(member.getEmail())) {
        if(result != null) {
            log.info("로그인 성공!");
            log.info("result : " + result);
            log.info("result.email : " + result.getEmail());
            model.addAttribute("userId", result.getEmail());
            model.addAttribute("userName", result.getName());
            session.setAttribute("userId", result.getEmail());
            return "/userView/oLoginForm";
        }
        return "redirect:/qLoginForm";
    }

    /** 기본 회원가입 폼 */
    @RequestMapping(value="/qJoinForm")
    public String qJoinForm() {
        log.info("MemberController - qJoinForm() 실행");
        return "/userView/oJoinForm";
    }

    /** 기본 회원가입 */
    @PostMapping(value="/qJoinBasic")
    public String qJoinBasic(MemberDTO memberDTO, Model model) {
        log.info("MemberController - qJoinBasic() 실행");
        log.info("memberDTO : " + memberDTO);

        MemberEntity member = new MemberEntity();
        member.setEmail(memberDTO.getEmail());  // 이메일
        member.setName(memberDTO.getName());  // 이름
        member.setPwd(memberDTO.getPwd());  // 비밀번호

//        log.info("member name : " + member.getName());
//        log.info("member email : " + member.getEmail());
        model.addAttribute("member",member);

        MemberEntity entity = memberDTO.toEntity();
        log.info("entity : " + entity.toString());

        qMemberServiceImpl.joinBasic(member);
        log.info("qMemberService.joinBasic(member) 실행 한 후");
        
        // DB save 안돼서 회원전체조회 테스트
        List<MemberEntity> members = qMemberServiceImpl.findMembers();
        log.info("members 값 : {}", members);
        model.addAttribute("members",members);

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

    /** 마이페이지 매핑 */
    @GetMapping(value="/qMyPage")
    public String qMyPage() {
        return "/userView/oMyPage";
    }

    /** 내정보수정 페이지 매핑 */
    @GetMapping(value="/qMyInfoUpdate")
    public String qMyInfoUpdate() {
        return "/userView/oMyInfoUpdate";
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