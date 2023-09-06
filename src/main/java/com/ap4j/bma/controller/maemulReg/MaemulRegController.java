package com.ap4j.bma.controller.maemulReg;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.service.maemulReg.MaemulRegService;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@SessionAttributes({"loginMember", "maemulRegEntity"})
@Controller
@Slf4j
public class MaemulRegController {

    @Autowired
    private MaemulRegService maemulRegService;

    //약관동의 페이지
    @GetMapping("/agree")
    public String agreementForm() {
        return "maemulReg/agree";
    }

    //매물정보 입력 페이지
    @GetMapping("/maemulinfo")
    public String maemulinfoForm(Model model) {
        model.addAttribute("maemulRegEntity", new MaemulRegEntity());
        return "maemulReg/maemulInfo";
    }

    //매뮬정보저장
    @PostMapping("/maemulinfo")
    public String saveMaemulInfo(@ModelAttribute MaemulRegEntity maemulRegEntity, Model model) {
        // 매물 정보를 임시로 세션에 저장
        model.addAttribute("maemulRegEntity", maemulRegEntity);
        return "redirect:/moreinfo";
    }

    // 상세 정보 입력 페이지
    // 상세 정보 입력 페이지
    @GetMapping("/moreinfo")
    public String moreinfoForm(Model model) {
        if (!model.containsAttribute("maemulRegEntity")) {
            return "redirect:/maemulinfo"; // 세션에 매물 정보가 없으면 다시 입력 페이지로 이동
        }
        return "maemulReg/moreinfo";
    }

    // 상세 정보 저장
    @PostMapping("/moreinfo")
    public String saveMoreInfo(@ModelAttribute MaemulRegEntity maemulRegEntity, RedirectAttributes redirectAttributes) {
        // 매물 정보를 데이터베이스에 저장
        MaemulRegEntity savedEntity = maemulRegService.saveMaemulInfo(maemulRegEntity);

        // 저장된 매물 정보의 ID를 리다이렉트 시에 전달
        redirectAttributes.addAttribute("maemulId", savedEntity.getId());

        return "redirect:/confirmation";
    }

    // 확인 페이지
    @GetMapping("/confirmation")
    public String confirmationPage(@RequestParam("maemulId") Integer maemulId, Model model) {
        // 매물 정보를 데이터베이스에서 가져와서 확인 페이지에 표시
        MaemulRegEntity maemulRegEntity = maemulRegService.getMaemulById(maemulId);
        model.addAttribute("maemulRegEntity", maemulRegEntity);
        return "maemulReg/confirmation";
    }

    @PostMapping("/confirmation")
    public void maemulSaveCoordinates(Integer maemulId, Double latitude, Double longitude) {
        maemulRegService.updateMeamulReg(maemulId, latitude, longitude);
    }

}

