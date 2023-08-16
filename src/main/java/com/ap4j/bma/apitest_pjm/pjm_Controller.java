package com.ap4j.bma.apitest_pjm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class pjm_Controller {
    
    @RequestMapping("/kakaoMap1")
    String kakaoMap1(Model model) {
        log.info("pjm컨트롤러 - kakaoMap1() 실행");

        List<pjm_MapInfo> mapList = new ArrayList<pjm_MapInfo>();
        // 원하는 곳 위도 경도를 찾아서 저장
        mapList.add(new pjm_MapInfo("카카오","제주시 어쩌고",33.450705,126.570677));
        mapList.add(new pjm_MapInfo("생태연못","제주시 어쩌구",33.450936,126.569477));
        mapList.add(new pjm_MapInfo("텃밭","제주시 어쩌구",33.450879,126.569940));
        mapList.add(new pjm_MapInfo("근린공원","제주시 어쩌구",33.451393,126.570738));

        double x = 0.0;
        double y = 0.0;

        for(pjm_MapInfo map : mapList) {
            x += map.getX();
            y += map.getY();
        }

        x = x/mapList.size();
        y = y/mapList.size();

        model.addAttribute("mapList", mapList);
        model.addAttribute("x",x);
        model.addAttribute("y",y);

        return "/apitest_pjm/pjm_map";
    }

    @RequestMapping("/kakaoMap2")
    public String kakaoMap2(Model model) {
        log.info("pjm컨트롤러 - kakaoMap2() 실행");

        return "apitest_pjm/pjm_maptest";

    }    @RequestMapping("/kakaoMap3")
    public String kakaoMap3(Model model) {
        log.info("pjm컨트롤러 - kakaoMap3() 실행");

        return "apitest_pjm/pjm_maptest2";
    }

}

// 카카오 js 키 053788c16dd13e251b14aca3da30755d