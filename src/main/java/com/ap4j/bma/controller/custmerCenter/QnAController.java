package com.ap4j.bma.controller.custmerCenter;

import com.ap4j.bma.model.entity.customerCenter.AnswerEntity;
import com.ap4j.bma.model.entity.customerCenter.QnAEntity;
import com.ap4j.bma.model.repository.QnARepository;
import com.ap4j.bma.service.customerCenter.QnAService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@SessionAttributes("loginMember")
@Controller
public class QnAController {

    @Autowired
    private QnAService qnAService;
    @Autowired
    private QnARepository qnARepository;

    //QNA
    @GetMapping("/qna")
    public String QnAWriteForm(HttpSession session, Model model) {
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
        model.addAttribute("searchUrl", "/member/qMyQnA");

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

    // 게시글 삭제 요청 처리
    @GetMapping("/deleteQnA")
    public String deleteQnA(@RequestParam("id") Integer id, Model model) {
        // 게시글 삭제
        qnAService.deleteQnA(id);

        // 전체 게시글 개수 업데이트
        int totalQnACount = qnARepository.countAllQnA();

        model.addAttribute("totalQnACount", totalQnACount);

        return "redirect:/member/qMyQnA"; // 게시글 목록 페이지로 리다이렉트
    }

}