package com.ap4j.bma.controller.custmerCenter;

import com.ap4j.bma.model.entity.customerCenter.QnAEntity;
import com.ap4j.bma.service.customerCenter.QnAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Slf4j
@SessionAttributes("loginMember")
@Controller
public class QnAController {

    @Autowired
    private QnAService qnAService;

    //QNA
    @GetMapping("/qna")
    public String QnAWriteForm(HttpSession session) {
        // 로그인 안했을 경우 접근제한 페이지 표시
        if (session.getAttribute("loginMember") == null) {
            return "userView/loginNeed";
        }

        return "customerCenter/QnABoard/QnAWrite";
    }

    @PostMapping("/qna")
    public String savaQnA(@ModelAttribute QnAEntity qnAEntity, Model model, @RequestParam("file") MultipartFile file) throws Exception {

        qnAService.saveQnA(qnAEntity, file);


        model.addAttribute("message", "1:1 문의가 등록되었습니다.");
        model.addAttribute("searchUrl", "/qna");

        return "community/message";
    }

    @GetMapping("/qnaview")
    public String QnAView(@RequestParam("id") Integer id, Model model) {
        // 글 ID를 이용하여 글 정보를 조회
        QnAEntity qna = qnAService.findById(id);

        // 조회한 글 정보를 모델에 추가하여 뷰로 전달
        model.addAttribute("qna", qna);

        return "customerCenter/QnABoard/QnAView"; // 상세보기 페이지로 이동
    }
}