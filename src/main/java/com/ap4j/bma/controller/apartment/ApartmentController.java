package com.ap4j.bma.controller.apartment;

import com.ap4j.bma.model.entity.apt.AptDTO;
import com.ap4j.bma.service.apartment.ApartmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ApartmentController {

    @Autowired
    ApartmentServiceImpl aptServiceImpl;

    /** 전국 아파트 리스트 api DB저장 */
    @GetMapping("csyTest")
    public String apartmentTest(Model model) {
        // DB 저장 메서드(init), api 호출 메서드(callApi)
        aptServiceImpl.init(aptServiceImpl.callApi());
        return "kakaoMap/apiTest";
    }

    /** DB정보를 기반으로 지도에서 좌표값 가져오기  */
    @GetMapping("csy")
    public String csy(Model model) {
        model.addAttribute("aptList", aptServiceImpl.aptList());
        return "kakaoMap/aptMain";
    }

    /** 가져온 좌표값 DB에 추가하기 */
    @PostMapping("/saveCoordinates")
    public String saveCoordinates(String latitude, String longitude, String aptName) {
        System.out.println("Updating coordinates for: " + aptName);
        aptServiceImpl.updateCoordinatesByAptName(aptName, latitude, longitude);

        return "redirect:/csy";

    }



}
