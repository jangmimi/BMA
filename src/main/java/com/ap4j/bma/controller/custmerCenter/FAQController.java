package com.ap4j.bma.controller.custmerCenter;

import com.ap4j.bma.model.entity.customerCenter.FAQEntity;
import com.ap4j.bma.service.customerCenter.FAQService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class FAQController {
    @Autowired
    private FAQService faqService;

    //FAQ
    @GetMapping("/faq/list")
    public String FAQList(Model model) {
        List<FAQEntity> faq = faqService.getAllFAQ();
        model.addAttribute("faq", faq);
        return "customerCenter/FAQBoard/FAQList";
    }

    @GetMapping("/faq/view")
    public String faqView(Model model, Integer id) {

        model.addAttribute("article", faqService.faqView(id));
        return "customerCenter/FAQBoard/FAQView";
    }
}