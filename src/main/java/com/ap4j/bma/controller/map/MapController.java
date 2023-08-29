package com.ap4j.bma.controller.map;

import com.ap4j.bma.model.entity.apt.AptDTO;
import com.ap4j.bma.service.apartment.ApartmentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("map")
public class MapController {

    @Autowired
    ApartmentServiceImpl aptServiceImpl;
//    @RequestMapping("main")
//    public String main(){
//        log.info("MapController.main.execute");
//        return "kakaoMap/markerCluster";
//    }
    /** 화면 좌표 범위의 DB값 데이터 보내주기 (클라이언트가 사용할 페이지)*/
    @PostMapping("/main")
    public ResponseEntity<List<AptDTO>> getMarker(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng) {
        List<AptDTO> aptList = aptServiceImpl.findAptListBounds(southWestLat, southWestLng, northEastLat, northEastLng);
        return ResponseEntity.ok(aptList);
    }
    @GetMapping("/main")
    public String getMarker() {
        return "kakaoMap/markerCluster";
    }

    @RequestMapping("map")
    public String map(){
        log.info("MapController.map.execute");
        return "map/map";
    }
}
