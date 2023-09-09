package com.ap4j.bma.controller.member;

import com.ap4j.bma.model.entity.member.MailDTO;
import com.ap4j.bma.model.entity.member.MemberEntity;
import com.ap4j.bma.service.member.MailService;
import com.ap4j.bma.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;
import java.util.Optional;

@Controller
public class MailController {

    @Autowired
    private MailService mailService;

    @Autowired
    private MemberService memberService;

    // 이메일 보내기
    @Transactional
    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam("userEmail") String userEmail, Model model) {
        Optional<MemberEntity> existMember = memberService.findByEmail(userEmail);

        // sns계정은 비밀번호 변경 불가로 메일 발송 없이 안내문구
        if (existMember.isEmpty()) {
            model.addAttribute("findPwdFailed", "일치하는 회원정보가 없습니다.");
            return "userView/findMemberInfo";
        }

        MemberEntity memberEntity = existMember.get();

        if (memberEntity.getRoot() != 1) {
            model.addAttribute("findPwdFailed", "간편 로그인(회원가입) 회원님께서는 해당 사이트에서 계정 정보를 확인해주세요.");
            return "userView/findMemberInfo";
        }

        if (memberEntity.getMember_leave()) {   // 탈퇴회원 체크
            model.addAttribute("findPwdFailed", "일치하는 회원정보가 없습니다.");
            return "userView/findMemberInfo";
        }

        MailDTO dto = mailService.createMailAndChangePassword(userEmail);
        mailService.mailSend(dto);

        return "userView/oLoginForm";
    }
}
