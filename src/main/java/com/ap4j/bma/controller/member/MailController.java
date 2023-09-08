package com.ap4j.bma.controller.member;

import com.ap4j.bma.model.entity.member.MailDTO;
import com.ap4j.bma.service.member.EmailService;
import com.ap4j.bma.service.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@Controller
public class MailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private MemberService memberService;

    // 이메일 보내기
    @Transactional
    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam("userEmail") String userEmail, Model model) {
        // 회원여부 체크 후 메일 발송
        boolean exist = memberService.existsByEmail(userEmail);

        if(exist) {
            MailDTO dto = emailService.createMailAndChangePassword(userEmail);
            emailService.mailSend(dto);

        } else {
            model.addAttribute("findPwdFailed", "일치하는 회원정보가 없습니다.");
            return "userView/findMemberInfo";
        }
        return "redirect:/member/qLoginForm";
    }
}
