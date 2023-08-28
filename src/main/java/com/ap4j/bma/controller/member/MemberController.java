package com.ap4j.bma.controller.member;
// pjm - use m o p q
// controller -q | model -m | html -o | etc.. -p
import com.ap4j.bma.config.PasswordEncoderConfig;
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.entity.member.MemberEntity;
import com.ap4j.bma.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.*;
import javax.validation.Valid;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;

@Slf4j
//@SessionAttributes("loginMember")
@RequestMapping("/member")
@Controller
public class MemberController {

    @Autowired
    private MemberService qMemberService;

    @Autowired
    private PasswordEncoderConfig pwdConfig;

    /** 로그인 페이지 매핑 */
    @RequestMapping(value="/qLoginForm")
    public String qLoginForm(@CookieValue(value = "rememberedEmail", required = false) String rememberedEmail ,Model model) {
        log.info("MemberController - qLoginForm() 실행");
        // 이메일 기억하기 쿠키가 있는 경우 해당 이메일을 화면에 표시
        if(rememberedEmail != null) {
            // view에 rememberedEmail 전달
            model.addAttribute("rememberedEmail", rememberedEmail);
        } else {
            model.addAttribute("rememberedEmail", null);
        }
        model.addAttribute("memberDTO", new MemberDTO());
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
//        MemberDTO userInfo = qMemberServiceImpl.getUserInfo2(accessToken);   // 사용자 정보 받음
        HashMap<String, Object> userInfo = qMemberService.getUserInfo(accessToken);   // 사용자 정보 받음
        log.info("login info : " + userInfo.toString());
        // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
        log.info("login userUniqueId : " + userInfo.get("id"));
        log.info("login userEmail : " + userInfo.get("email"));
        log.info("login userName : " + userInfo.get("nickname"));
//        log.info("login thumbnail_image_url : " + userInfo.get("thumbnail_image_url"));

        boolean emailCheck = qMemberService.existsByEmail((String) userInfo.get("email"));

        if(emailCheck) {
            log.info("존재하는 이메일입니다. 로그인을 진행합니다.");    // 이미 가입된 계정이면 로그인으로 진행하기
            MemberDTO kloginMember = new MemberDTO();
            kloginMember.setEmail((String) userInfo.get("email"));
            kloginMember.setName((String) userInfo.get("nickname"));

            kloginMember = qMemberService.login(kloginMember);
            return "redirect:/member/qMyPage";

        } else {
            MemberEntity member = new MemberEntity();
            member.setEmail((String) userInfo.get("email"));
            member.setName((String) userInfo.get("nickname"));
            member.setRoot("카카오");

            //member.setPwd(pwdConfig.passwordEncoder().encode((String) userInfo.get("email")));   // 암호화는 이메일 암호화로
            log.info(member.toString());
            qMemberService.joinBasic(member);   // 카카오정보로 회원가입 실행

            if(userInfo.get("email") != null) {
                session.setAttribute("userEmail", userInfo.get("email"));
                session.setAttribute("userName", userInfo.get("nickname"));
                session.setAttribute("accessToken", accessToken);
            }
            model.addAttribute("userEmail", userInfo.get("email"));
            model.addAttribute("userName", userInfo.get("nickname"));
        }

//        model.addAttribute("thumbnail_image_url", userInfo.get("thumbnail_image_url"));
//        if(userInfo.getEmail() != null) {
//            session.setAttribute("userId", userInfo.getEmail());
//            session.setAttribute("userName", userInfo.getName());
//            session.setAttribute("accessToken", accessToken);
//        }
//        model.addAttribute("userId", userInfo.getEmail());
//        model.addAttribute("userName", userInfo.getName());

        return "/userView/oMyPage";
    }

    /** 카카오 로그아웃 */
    @RequestMapping(value="/qLogout")
    public String qLogout(HttpSession session) {
        log.info("MemberController - qLogout() 실행");

        /* token 재사용으로 콘솔에 400에러가 확인되어 주석 처리(정확한 원인 파악 필요) */
//        qMemberServiceImpl.kakaoLogout((String)session.getAttribute("accessToken"));
//        session.removeAttribute("accessToken");
        session.removeAttribute("userEmail");
        session.removeAttribute("userName");

        log.info("로그아웃 완료되었습니다.");  // 로그아웃 후 메인화면 리다이렉트
        log.info("--- 로그아웃 후 세션 상태 체크 ---");
        log.info((String) session.getAttribute("userEmail"));
        log.info((String) session.getAttribute("userName"));
        return "redirect:/";
    }

    /** 기본 로그인 */
    @PostMapping(value="/qLoginBasic")
    public String qBasicLogin(@ModelAttribute MemberDTO memberDTO, @RequestParam(required = false) boolean oSaveId,
                              Model model, HttpSession session, HttpServletResponse response) { //BindingResult bindingResult
        log.info("MemberController - qBasicLogin() 실행");
        log.info("memberDTO : " + memberDTO);

        MemberDTO loginMember = qMemberService.login(memberDTO);
        if(loginMember != null) {
            log.info(loginMember.toString());
            log.info("로그인 성공");

            loginMember.toEntity();
            String userEmail = loginMember.getEmail();
            String userName = loginMember.getName();

            // 쿠키 설정
            if(oSaveId) {
//                Cookie idCookie = new Cookie("userEmail",  String.valueOf(loginMember.getEmail()));
                log.info("쿠키 생성중");
                Cookie cookie = new Cookie("rememberedEmail", loginMember.getEmail());
                cookie.setMaxAge(30 * 24 * 60 * 60);    // 30일 동안 유지
                cookie.setPath("/");
                response.addCookie(cookie);
                log.info("cookie : " + cookie);
            } else {
                Cookie cookie = new Cookie("rememberedEmail", null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }

            session.setAttribute("userEmail", userEmail);
            session.setAttribute("userName", userName);
            session.setAttribute("loginMember", loginMember);

            model.addAttribute("userEmail", userEmail);
            model.addAttribute("userName",userName);
            model.addAttribute("loginMember",loginMember);

            log.info("loginMember : " + loginMember.toString());

            return "/userView/oMyPage";
        } else {
            log.info("로그인 실패헀어요.");
            model.addAttribute("msg","로그인 실패");
            return "redirect:/member/qLoginForm";
//            model.addAttribute("goURL","redirect:/member/qLoginForm");
        }
/*        MemberEntity member = new MemberEntity();
        member.setEmail(memberDTO.getEmail());
        member.setPwd(memberDTO.getPwd());
        log.info(member.toString());
        
        if(bindingResult.hasErrors()) {
            log.info("유효성 검사 에러 발생");
            return "/userView/oLoginForm";
        }

        MemberEntity result = qMemberService.login(member.getEmail());

//        if(qMemberServiceImpl.loginByEmail(member.getEmail())) {
        if(result != null) {
            log.info("로그인 성공!");
            log.info("result : " + result);
            log.info("result.email : " + result.getEmail());
            model.addAttribute("userId", result.getEmail());
            model.addAttribute("userName", result.getName());
            session.setAttribute("userId", result.getEmail());
            return "/userView/oLoginForm";
        }*/
//        return "/userView/oMyPage";
//        return "/userView/oLoginForm";    // 원래 사용 코드
//        return "redirect:/member/qLoginForm";
    }

    /** 기본 회원가입 폼 */
    @RequestMapping(value="/qJoinForm")
    public String qJoinForm(Model model) {
        log.info("MemberController - qJoinForm() 실행");
        model.addAttribute("memberDTO", new MemberDTO());
        return "/userView/oJoinForm";
    }

    /** 기본 회원가입 */
    @PostMapping(value="/qJoinBasic")
    public String qJoinBasic(@ModelAttribute MemberDTO memberDTO, Model model) {    //BindingResult bindingResult,  // -> model 앞에 위치해야함
        log.info("MemberController - qJoinBasic() 실행");
        log.info("memberDTO : " + memberDTO);

        MemberEntity member = new MemberEntity();
        member.setEmail(memberDTO.getEmail());
        member.setName(memberDTO.getName());
        member.setPwd(pwdConfig.passwordEncoder().encode(memberDTO.getPwd()));  // 암호화 비밀번호로 set
        member.setNickname(memberDTO.getNickname());
        member.setTel(memberDTO.getTel());
        member.setRoot("기본회원");

        model.addAttribute("member",member);

        MemberEntity entity = memberDTO.toEntity();
        log.info("entity : " + entity.toString());

        qMemberService.joinBasic(member);
        log.info("qMemberService.joinBasic(member) 실행 한 후");
        
        // 회원전체조회 테스트
        List<MemberEntity> members = qMemberService.findMembers();
        log.info("members 값 : {}", members);
        model.addAttribute("members",members);

        return "redirect:/member/qLoginForm";
    }

    @RequestMapping(value="/testnaverLogin")
    public String textnaver() {
        return "/userView/pjm_naverLogin";
    }

    /** 네이버 로그인 */
    @RequestMapping(value="/qLoginNaver")
    public String qLoginNaver(@RequestParam("code") String code,
                              @RequestParam("state") String state, Model model, HttpSession session) {
        log.info("MemberController - qLoginNaver() 실행");
        log.info("####code : " + code);

        // 1번 인증코드 요청 전달
        String accessToken = qMemberService.getAccessTokenNaver(code);     // code로 토큰 받음
        log.info("accessToken : " + accessToken);

//        // 2번 인증코드로 토큰 전달
        HashMap<String, Object> userInfo = qMemberService.getUserInfo(accessToken);   // 사용자 정보 받음
        log.info("login info : " + userInfo.toString());
        // https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#req-user-info
        log.info("login userUniqueId : " + userInfo.get("id"));
        log.info("login userEmail : " + userInfo.get("email"));
        log.info("login userName : " + userInfo.get("nickname"));

        if(userInfo.get("email") != null) {
            session.setAttribute("userEmail", userInfo.get("email"));
            session.setAttribute("userName", userInfo.get("nickname"));
            session.setAttribute("accessToken", accessToken);
        }
        model.addAttribute("userEmail", userInfo.get("email"));
        model.addAttribute("userName", userInfo.get("nickname"));

        MemberEntity member = new MemberEntity();
        member.setEmail((String) userInfo.get("email"));  // 비밀번호
        member.setName((String) userInfo.get("nickname"));  // 아이디
        log.info(member.toString());

        qMemberService.joinBasic(member);   // 네이버정보로 회원가입 실행

        return "redirect:/qLoginForm";
    }

    @GetMapping(value="/qLoginNaverCallback")
    public String qLoginNaverCallback(@RequestParam String code) {
        log.info("MemberController - qLoginNaverCallback() 실행");

        return "code : " + code;
//        return "redirect:/qLoginForm";
    }

    /** 네이버 로그아웃 */
    @RequestMapping(value="/qLogoutNaver")
    public String qLogoutNaver(HttpSession session) {
        log.info("MemberController - qLogout() 실행");

        /* token 재사용으로 콘솔에 400에러가 확인되어 주석 처리(정확한 원인 파악 필요) */
//        qMemberServiceImpl.kakaoLogout((String)session.getAttribute("accessToken"));
//        session.removeAttribute("accessToken");
        session.removeAttribute("userEmail");
        session.removeAttribute("userName");

        log.info("로그아웃 완료되었습니다.");  // 로그아웃 후 메인화면 리다이렉트

        return "redirect:/";
    }

    /** 마이페이지 매핑 */
    @RequestMapping(value="/qMyPage")
    public String qMyPage(HttpSession session, Model model) {
        log.info("MemberController - qMyPage() 실행");
        String userEmail = (String) session.getAttribute("userEmail");
        String userName = (String) session.getAttribute("userName");
        model.addAttribute("userEmail",userEmail);
        model.addAttribute("userName",userName);
        return "/userView/oMyPage";
    }

    /** 내정보 수정페이지 매핑 */
    @GetMapping(value="/qMyInfoUpdate")
    public String qMyInfoUpdate(HttpSession session, Model model) {
        log.info("MemberController - qMyInfoUpdate() 실행");
        String userEmail = (String) session.getAttribute("userEmail");
        String userName = (String) session.getAttribute("userName");
        model.addAttribute("userEmail",userEmail);
        model.addAttribute("userName",userName);

        log.info("로그인중인 userEmail : " + userEmail);
        MemberEntity findmem = qMemberService.getMemberOne(userEmail);
        model.addAttribute("idx",findmem.getIdx());
        log.info("로그인중인 findmem : " + findmem);
        return "/userView/oMyInfoUpdate";
    }

    /** 내정보 수정하기 */
    @PostMapping(value="/qUpdateMember/{idx}")
    public String qUpdate(@PathVariable(required = false) Long idx, @ModelAttribute MemberEntity updatedMember, Model model, HttpSession session) {
        log.info("MemberController - qUpdate() 실행");
        //        updatedMember.setIdx(idx);
//        qMemberService.getMemberOne(idx);
        qMemberService.updateMember(idx, updatedMember);
        log.info("회원정보 수정 완료 (수정 후) : " + updatedMember);
        session.setAttribute("userName", updatedMember.getName());

        model.addAttribute("member", updatedMember);
        model.addAttribute("userName", updatedMember.getName());
        model.addAttribute("userEmail", updatedMember.getEmail());
        return "redirect:/member/qMyPage";
    }

    /** 기본 회원탈퇴 */   // sns는 별도 처리 해줘야 함
    @PostMapping("/qDeleteMember/{idx}")
    public String deleteMember(@PathVariable(required = false) Long idx, HttpSession session) {
        log.info("MemberController - deleteMember() 실행");
        qMemberService.deleteMemberByIdx(idx);  // idx 기준으로 회원탈퇴 처리
        session.invalidate();                   // 회원탈퇴 후 세션 전체 삭제
        return "redirect:/";
    }

    /** 이메일/비밀번호 찾기 페이지 매핑 */
    @RequestMapping(value="/qFindMemberInfo")
    public String qFindMemberInfo() {
        return "/userView/oFindMemberInfo";
    }

    /** 이메일 중복 체크 (js ajax 활용) */
    @PostMapping("/qEmailCheck")
    @ResponseBody
    public int qEmailCheck(@RequestParam("email") String email) {
        int cnt = 0;
        boolean emailCheck = qMemberService.existsByEmail(email);
        cnt = emailCheck ? 1 : 0;
        return cnt;
    }

}
