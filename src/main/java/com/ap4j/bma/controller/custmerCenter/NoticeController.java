package com.ap4j.bma.controller.custmerCenter;

import com.ap4j.bma.model.entity.customerCenter.NoticeEntity;
import com.ap4j.bma.service.customerCenter.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


import java.util.List;

@SessionAttributes("userEmail")
@Controller
public class NoticeController {
    @Autowired
    private NoticeService noticeService;


    //notice
    @GetMapping("/notice/list")
    public String noticeList(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        int pageSize = 10; // 한 페이지당 보여줄 게시글 개수
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").descending());
        Page<NoticeEntity> noticePage = noticeService.getNoticePage(pageable);

        model.addAttribute("notices", noticePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", noticePage.getTotalPages());

        return "customerCenter/noticeBoard/noticeList";
    }

    @GetMapping("/notice/view")
    public String noticeView(Model model , Integer id){

        model.addAttribute("article",noticeService.noticeView(id));
        return "customerCenter/noticeBoard/noticeView";
    }

}
