package com.ap4j.bma.controller.member;

import com.ap4j.bma.model.entity.member.MailDTO;
import com.ap4j.bma.service.member.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@Controller
public class MailController {

    @Autowired
    private EmailService emailService;

    // 이메일 보내기
    @Transactional
    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam("userEmail") String userEmail) {
        MailDTO dto = emailService.createMailAndChangePassword(userEmail);
        emailService.mailSend(dto);

        return "member/qLoginForm";
    }
}
