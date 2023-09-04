package com.ap4j.bma.controller.maemulReg;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.service.maemulReg.MaemulRegService;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@SessionAttributes("loginMember")
@Controller
@Slf4j
public class MaemulRegController {

    @Autowired
    private MaemulRegService maemulRegService;

    @GetMapping("/agree") //약관동의 페이지
    public String agreementForm() {
        return "maemulReg/agree";
    }

    //매물정보페이지
    @GetMapping("/maemulinfo")
    public String maemulinfoForm(Model model) {
        model.addAttribute("maemulRegEntity", new MaemulRegEntity());
        return "maemulReg/maemulInfo";
    }

    //매뮬정보저장
    @PostMapping("/maemulinfo")
    public String saveMaemulInfo(@ModelAttribute MaemulRegEntity maemulRegEntity, RedirectAttributes redirectAttributes) {
        // 매물 정보를 서비스를 통해 저장
        MaemulRegEntity savedEntity = maemulRegService.saveMaemulInfo(maemulRegEntity);

        // 저장된 매물 정보의 ID를 리다이렉트 시에 전달
        redirectAttributes.addAttribute("maemulId", savedEntity.getId());

        return "redirect:/moreinfo";
    }

    //상세정보페이지
    @GetMapping("/moreinfo")
    public  String moreinfoForm(){

        return "maemulReg/moreinfo";
    }

}
