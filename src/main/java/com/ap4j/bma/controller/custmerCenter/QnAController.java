package com.ap4j.bma.controller.custmerCenter;

import com.ap4j.bma.model.entity.customerCenter.QnAEntity;
import com.ap4j.bma.service.customerCenter.QnAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;

@SessionAttributes("loginMember")
@Controller
public class QnAController {

    @Autowired
    private QnAService qnAService;

    //QNA
    @GetMapping("/qna")
    public String QnAWriteForm(HttpSession session) {
        // 로그인 안했을 경우 접근제한 페이지 표시
        if(session.getAttribute("loginMember") == null) { return "userView/loginNeed"; }

        return "customerCenter/QnABoard/QnAWrite";
    }

    @PostMapping("/qna")
    public String savaQnA(@ModelAttribute QnAEntity qnAEntity, Model model) {

        qnAService.saveQnA(qnAEntity);

        model.addAttribute("message", "1:1 문의가 등록되었습니다..");
        model.addAttribute("searchUrl", "/qna");

        return "community/message";
    }
}