package com.ap4j.bma.controller.maemulReg;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.repository.MaemulRegRepository;
import com.sun.xml.bind.v2.model.core.PropertyInfo;
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
public class MaemulRegController {

    @Autowired
    private MaemulRegRepository maemulRegRepository;

    @GetMapping("/agree") //약관동의 페이지
    public String agreementForm() {

        return "maemulReg/agree";
    }

    //매물정보페이지
    @GetMapping("/maemulinfo")
    public String maemulinfoForm(Model model) {

        return "maemulReg/maemulInfo";
    }

    //매물 정보 저장
    @PostMapping("/maemulinfo")
    public String saveMaemulInfo(@ModelAttribute MaemulRegEntity maemulRegEntity) {
        // 매물 정보를 데이터베이스에 저장
        maemulRegRepository.save(maemulRegEntity);
        return "redirect:/moreinfo";
    }

    //상세정보페이지
    @GetMapping("/moreinfo")
    public  String moreinfoForm(){

        return "maemulReg/moreinfo";
    }

}
