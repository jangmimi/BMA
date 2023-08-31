package com.ap4j.bma.controller.custmerCenter;

import com.ap4j.bma.model.entity.customerCenter.NoticeEntity;
import com.ap4j.bma.service.customerCenter.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class NoticeController {
    @Autowired
    private NoticeService noticeService;


    //notice
    @GetMapping("/notice/list")
    public  String noticeList(Model model){
        List<NoticeEntity> notices = noticeService.getAllNotices();
        model.addAttribute("notices", notices);
        return "customerCenter/noticeBoard/noticeList";
    }

    @GetMapping("/notice/view")
    public String noticeView(Model model , Integer id){

        model.addAttribute("article",noticeService.noticeView(id));
        return "customerCenter/noticeBoard/noticeView";
    }

}
