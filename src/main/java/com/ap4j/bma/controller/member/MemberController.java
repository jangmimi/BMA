package com.ap4j.bma.controller.member;
// pjm - use m o p q
// controller -q | model -m | html -o | etc.. -p
import com.ap4j.bma.config.PasswordEncoderConfig;
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.entity.member.MemberEntity;
import com.ap4j.bma.service.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.*;
import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.Optional;

@Slf4j
@SessionAttributes("loginMember")
@RequestMapping("/member")
@Controller
public class MemberController {
//hi
    @Autowired
    private MemberService qMemberService;

    @Autowired
    private PasswordEncoderConfig pwdConfig;    // 비밀번호 암호화 객체 생성

    /** 로그인 페이지 매핑 */
    @RequestMapping("/qLoginForm")
    public String qLoginForm(@CookieValue(value = "rememberedEmail", required = false) String rememberedEmail, Model model) {
        log.info("MemberController - qLoginForm() 실행");

        // 쿠키가 있는 경우는 해당 이메일을 화면에 표시
        model.addAttribute("rememberedEmail", rememberedEmail);
        return "userView/oLoginForm";
    }

    /** 카카오 로그인 실행 */
    @RequestMapping("/qLogin")
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
        log.info("login userEmail : " + userInfo.get("email"));
        log.info("login userName : " + userInfo.get("name"));
        log.info("login userNickname : " + userInfo.get("nickname"));
        log.info("login userPhone_number : " + userInfo.get("phone_number"));
//        log.info("login thumbnail_image_url : " + userInfo.get("thumbnail_image_url"));

        boolean emailCheck = qMemberService.existsByEmail((String) userInfo.get("email"));  // DB내 이메일 존재 여부 체크
        MemberDTO kloginMember = new MemberDTO();

        if(emailCheck) {
            log.info("존재하는 이메일입니다. 로그인을 진행합니다.");    // 이미 가입된 계정이면 로그인 진행
            kloginMember.setEmail((String) userInfo.get("email"));
            kloginMember.setName((String) userInfo.get("name"));
            kloginMember.setNickname((String) userInfo.get("nickname"));
            kloginMember.setTel((String) userInfo.get("phone_number"));
            kloginMember = qMemberService.login(kloginMember);

            kloginMember.toEntity();

            model.addAttribute("loginMember",kloginMember);
            log.info("loginMember : " + kloginMember.toString());

            return "redirect:/";

        } else {
            kloginMember.setEmail((String) userInfo.get("email"));
            kloginMember.setName((String) userInfo.get("name"));
            kloginMember.setNickname((String) userInfo.get("nickname"));
            kloginMember.setTel((String) userInfo.get("phone_number"));
            kloginMember.setRoot(2);
            MemberEntity memberEntity = kloginMember.toEntity();
            //memberDTO.setPwd(pwdConfig.passwordEncoder().encode((String) userInfo.get("email")));   // 암호화는 이메일 암호화로
            qMemberService.joinBasic(memberEntity);   // 카카오정보로 회원가입 실행

//            if(userInfo.get("email") != null) {
//                session.setAttribute("userEmail", userInfo.get("email"));
//                session.setAttribute("userName", userInfo.get("nickname"));
//                session.setAttribute("accessToken", accessToken);
//            }
//            model.addAttribute("userEmail", userInfo.get("email"));
//            model.addAttribute("userName", userInfo.get("nickname"));
            model.addAttribute("loginMember", kloginMember);
        }
        return "redirect:/";
    }

    /** 로그아웃 */
    @RequestMapping("/qLogout")
    public String qLogout(SessionStatus sessionStatus) {
        log.info("MemberController - qLogout() 실행");
//        token 재사용으로 콘솔에 400에러가 확인되어 주석 처리(정확한 원인 파악 필요)
//        qMemberServiceImpl.kakaoLogout((String)session.getAttribute("accessToken"));
        sessionStatus.setComplete();
        log.info("--- 로그아웃 후 ---");
        return "redirect:/";
    }

    /** 기본 로그인 */
    @PostMapping("/qLoginBasic")
    public String qBasicLogin(@ModelAttribute MemberDTO memberDTO, @RequestParam(required = false) boolean oSaveId,
                              Model model, HttpSession session, HttpServletResponse response) {
        log.info("MemberController - qBasicLogin() 실행");
        log.info("memberDTO : " + memberDTO);

        MemberDTO loginMember = qMemberService.login(memberDTO);
        if(loginMember != null && !loginMember.getMember_leave()) {
            session.setAttribute("errorMsg", null);
            log.info(loginMember.toString());
            log.info("로그인 성공");

            loginMember.toEntity();
            session.setAttribute("loginMember",  loginMember);

            // 쿠키 설정
            if(oSaveId) {
                log.info("쿠키 생성중");
                Cookie cookie = new Cookie("rememberedEmail", loginMember.getEmail());
                cookie.setMaxAge(30 * 24 * 60 * 60);    // 30일 동안 유지
                cookie.setPath("/");                    // 모든 경로에 쿠키 설정
                response.addCookie(cookie);

            } else {
                Cookie cookie = new Cookie("rememberedEmail", null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
            return "redirect:/";

        } else {
            session.setAttribute("errorMsg","이메일 또는 패스워드를 다시 확인해주세요.");
            return "redirect:/member/qLoginForm";
        }
    }

    /** 기본 회원가입 폼 */
    @RequestMapping("/qJoinForm")
    public String qJoinForm() {
        log.info("MemberController - qJoinForm() 실행");
        return "userView/oJoinForm";
    }

    /** 기본 회원가입 */
    @PostMapping("/qJoinBasic")
    public String qJoinBasic(@ModelAttribute MemberDTO memberDTO, Model model) {    //BindingResult bindingResult,  // -> model 앞에 위치해야함
        log.info("MemberController - qJoinBasic() 실행");
        log.info("memberDTO : " + memberDTO);

        // pwd는 암호화해서 가입 경로와 별도로 세팅
        memberDTO.setPwd(pwdConfig.passwordEncoder().encode(memberDTO.getPwd()));
        // 약관 동의 체크 여부에 따라 값 저장
        memberDTO.setChoice1(Boolean.TRUE.equals(memberDTO.getChoice1()));
        memberDTO.setChoice2(Boolean.TRUE.equals(memberDTO.getChoice2()));
        memberDTO.setRoot(1); // 가입 경로에 따라 체크 후 지정 **
        MemberEntity entity = memberDTO.toEntity();
        log.info("memberDTO toEntity : " + entity.toString());  // pwd까지 나와서 추후 삭제예정

        qMemberService.joinBasic(entity);

        return "redirect:/member/qLoginForm";
    }

    @RequestMapping("/testnaverLogin")  // 삭제예정
    public String textnaver() {
        return "userView/pjm_naverLogin";
    }

    /** 네이버 로그인 */
    @RequestMapping("/qLoginNaver")
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

    @GetMapping("/qLoginNaverCallback")
    public String qLoginNaverCallback(@RequestParam String code) {
        log.info("MemberController - qLoginNaverCallback() 실행");

        return "code : " + code;
//        return "redirect:/qLoginForm";
    }

    /** 네이버 로그아웃 */
    @RequestMapping("/qLogoutNaver")
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
    @RequestMapping("/qMyPage")
    public String qMyPage(HttpSession session, Model model) {
        log.info("MemberController - qMyPage() 실행");

        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        log.info("qMyPage에서 loginMember 세션 확인 : " + loginMember.toString());

        return "userView/oMyPage";
    }

    /** 내정보 수정페이지 매핑 */
    @GetMapping("/qMyInfoUpdate")
    public String qMyInfoUpdate(HttpSession session, Model model) {
        log.info("MemberController - qMyInfoUpdate() 실행");

        MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
        log.info("qMyPage에서 loginMember 세션 확인 : " + loginMember.toString());

        return "userView/oMyInfoUpdate";
    }

    /** 내정보 수정하기 */
    @PostMapping("/qUpdateMember/{id}")
    public String qUpdate(@ModelAttribute MemberDTO updatedMember, Model model, HttpSession session) {
        log.info("MemberController - qUpdate() 실행");
        log.info("updatedMember : " + updatedMember);

        Long id =  ((MemberDTO) session.getAttribute("loginMember")).getId();

        MemberEntity memberEntity = qMemberService.updateMember(id, updatedMember);
        model.addAttribute("loginMember", memberEntity.toDTO());   // 수정 객체 지정
        log.info("회원정보 수정 완료 (수정 후) : " + memberEntity);

        return "redirect:/member/qMyPage";
    }

    /** 기본 회원탈퇴 */   // sns는 별도 처리 해줘야 함
    @PostMapping("/qLeaveMember/{id}")
    public String leaveMember(HttpSession session, SessionStatus sessionStatus) {
        log.info("MemberController - qLeaveMember() 실행");

        Long id =  ((MemberDTO) session.getAttribute("loginMember")).getId();
        qMemberService.leaveMember(id);
        sessionStatus.setComplete();          // 회원탈퇴 후 세션 전체삭제

        return "redirect:/";
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

    /** 이메일/비밀번호 찾기 페이지 매핑 */
    @RequestMapping("/qFindMemberInfo")
    public String qFindMemberInfo() {
        return "userView/oFindMemberInfo";
    }

    /** 이메일 찾기 */
    @PostMapping("/qFindEmail")
    public String qFindEmail(@RequestParam String name, @RequestParam String tel, Model model) {
        log.info("MemberController - qFindEmail() 실행");

        Optional<MemberEntity> find = qMemberService.findByNameAndTel(name, tel);
        if(find.isPresent()) {
            log.info("이메일찾기 : " + find.get().getEmail());
            boolean result = find.isPresent();
            model.addAttribute("findEmail", find.get().getEmail());
        } else {
            model.addAttribute("findEmailFailed", "일치하는 회원정보가 없습니다.");
        }
        return "userView/oFindMemberInfo";
//        return "redirect:/member/qFindMemberInfo";
    }
    // ajax로 변경
    @PostMapping("/qFindEmailCheck")
    @ResponseBody
    public String qFindEmailCheck(@RequestParam String name, @RequestParam String tel) {
        Optional<MemberEntity> find = qMemberService.findByNameAndTel(name, tel);
        String findEmail = find.get().getEmail();
        return findEmail;
    }

    /** 비밀번혼 찾기 */
    @PostMapping("/qFindPwd")
    public String qFindPwd(@RequestParam String emailpwd, @RequestParam String telpwd, Model model) {
        log.info("MemberController - qFindPwd() 실행");

        Optional<MemberEntity> find = qMemberService.findByEmailAndTel(emailpwd, telpwd);
        if(find.isPresent()) {
            log.info("비밀번호 찾기 : " + find.get().getPwd());
            boolean result = find.isPresent();
            model.addAttribute("findPwd", find.get().getPwd());
        } else {
            model.addAttribute("findPwdFailed", "일치하는 회원정보가 없습니다.");
        }
        return "userView/oFindMemberInfo";
//        return "redirect:/member/qFindMemberInfo";
    }
}

//
//package com.ap4j.bma.controller;
//
//        import com.ap4j.bma.model.entity.member.MemberDTO;
//        import lombok.extern.slf4j.Slf4j;
//        import org.springframework.stereotype.Controller;
//        import org.springframework.ui.Model;
//        import org.springframework.web.bind.annotation.RequestMapping;
//        import org.springframework.web.bind.annotation.SessionAttribute;
//        import org.springframework.web.bind.annotation.SessionAttributes;
//        import org.springframework.web.client.RestTemplate;
//        import org.springframework.web.context.annotation.SessionScope;
//
//        import javax.servlet.http.HttpSession;
//
//@SessionAttributes("loginMember")
//@Slf4j
//@Controller
//public class HomeController {
//
////	@RequestMapping("/")
////	public String test(){
////		log.info("HomeController.payments.execute");
////		return "payments/payments";
////	}
//
//    @RequestMapping("/")
//    public String mainPage(HttpSession session){
//        log.info("session loginMember : " + session.getAttribute("loginMember"));
//
////		if(session.getAttribute("loginMember") != null) {	// 로그인 시 전체 정보 출력이 아닌 id, email만 출력
////			MemberDTO loginMember = (MemberDTO) session.getAttribute("loginMember");
////			Long id = loginMember.getId();
////			String email = loginMember.getEmail();
////			log.info("session id : " + String.valueOf(id) + ", session email : " + email);
////		}
//        return "mainPage/mainPage";
//    }
//
//}
