package com.ap4j.bma.controller.map;

import com.ap4j.bma.model.entity.TalkTalk.TalkTalkReviewEntity;
import com.ap4j.bma.model.entity.apt.AptDTO;
import com.ap4j.bma.model.entity.apt.AptRealTradeDTO;
import com.ap4j.bma.model.entity.apt.HangJeongDongDTO;
import com.ap4j.bma.model.entity.meamulReg.MaeMulRegDTO;
import com.ap4j.bma.service.apartment.ApartmentServiceImpl;
import com.ap4j.bma.service.maemulReg.MaemulRegService;
import com.ap4j.bma.service.talktalk.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SessionAttributes("loginMember")
@Slf4j
@Controller
@RequestMapping("map")
public class MapController {
    @Autowired
    private ReviewService reviewService;


    @Autowired
    ApartmentServiceImpl aptServiceImpl;

    @Autowired
    MaemulRegService maemulRegService;

    /** 화면 좌표 범위의 DB값 데이터 보내주기 (클라이언트가 사용할 페이지)*/
    @PostMapping("/main")
    public ResponseEntity<Map<String, Object>> getMarker(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng, String roadName, String keyword, Integer zoomLevel) {
        // 화면 좌표값에 따른 행정동 리스트
        List<HangJeongDongDTO> hjdList = aptServiceImpl.findHJDListBounds(southWestLat, southWestLng, northEastLat, northEastLng, zoomLevel);
        // 화면 좌표값에 따른 아파트 리스트
        List<AptDTO> aptList = aptServiceImpl.findAptListBounds(southWestLat, southWestLng, northEastLat, northEastLng);
        // 도로명에 따른 실거래가 리스트
        List<AptRealTradeDTO> aptRealTradeDTOList = aptServiceImpl.findByRoadName(roadName);
        // 도로명, 구주소, 아파트명으로 검색시 해당 아파트 정보
        AptDTO aptSearch = aptServiceImpl.findByKeyword(keyword);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("aptList", aptList); // 아파트 리스트
        responseData.put("aptRealTradeDTOList", aptRealTradeDTOList); // 아파트 실거래가 리스트
        // 검색했을때만 aptSearch 객체를 전송한다.
        if (aptSearch != null) {
            responseData.put("aptSearch", aptSearch);
        }
        // 행정동 리스트 있을 때만 객체 전송
        if (hjdList != null) {
            responseData.put("hjdList", hjdList);
        }

        return ResponseEntity.ok(responseData);
    }

    //리뷰리스트 출력하는 메서드
    @GetMapping("/main")
    public String getMarker(HttpSession session, Model model) {
        log.info("map main 실행!");
        log.info("로그인한 회원정보~ : " + session.getAttribute("loginMember"));

        List<TalkTalkReviewEntity> list = reviewService.reviewList();
        log.info("맵컨트롤러에서 리뷰리스트출력~ : "+list.toString());

        //서비스에서 생성한 리스트를 list라는 이름으로 반환하겠다.
        model.addAttribute("list", list);

        return "kakaoMap/markerCluster";
    }



    @RequestMapping("map")
    public String map(){
        log.info("MapController.map.execute");
        return "map/map";
    }

    @PostMapping("map")
    public ResponseEntity<Map<String, Object>> map2(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng, Integer zoomLevel){
        log.info("MapController.map.execute");

        Map<String, Object> responseData = new HashMap<>();
        // 화면 좌표값에 따른 행정동 리스트
        List<HangJeongDongDTO> hjdList = aptServiceImpl.findHJDListBounds(southWestLat, southWestLng, northEastLat, northEastLng, zoomLevel);
        responseData.put("hjdList", hjdList);

        // 화면 좌표값에 따른 마커
        List<MaeMulRegDTO> maemulList = maemulRegService.findMaemulListBounds(southWestLat, southWestLng, northEastLat, northEastLng);
        responseData.put("maenulList", maemulList);
//        System.out.println("매물리스트 : " + maemulList);

        return ResponseEntity.ok(responseData);
    }
}