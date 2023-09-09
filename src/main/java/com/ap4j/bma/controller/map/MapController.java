package com.ap4j.bma.controller.map;

import com.ap4j.bma.model.entity.TalkTalk.TalkTalkReviewEntity;
import com.ap4j.bma.model.entity.apt.AptDTO;
import com.ap4j.bma.model.entity.apt.AptRealTradeDTO;
import com.ap4j.bma.model.entity.apt.HangJeongDongDTO;
import com.ap4j.bma.model.entity.meamulReg.MaeMulRegDTO;
import com.ap4j.bma.model.entity.member.LikedEntity;
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.repository.LikedRepository;
import com.ap4j.bma.service.apartment.ApartmentServiceImpl;
import com.ap4j.bma.service.maemulReg.MaemulRegService;
import com.ap4j.bma.service.talktalk.ReviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    @Autowired
    LikedRepository likedRepository;

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

    public ResponseEntity<Map<String, Object>> map2(HttpSession session, Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng, Integer zoomLevel, String address, String tradeType
                                                    ,Integer numberOfRooms, Integer numberOfBathrooms, Integer floorNumber, Integer managementFee, String Elevator
                                                    ,String direction, String Parking, String shortTermRental,  String keyword, String value, Integer rowSellingPrice, Integer highSellingPrice
                                                    , Integer rowDepositForLease, Integer highDepositForLease){
        System.out.println("컨트롤러 address " + address);
        System.out.println("컨트롤러 value = " + value);
        Map<String, Object> responseData = new HashMap<>();

        // 로그인 값 넘기기
        String nickName = null;
        if((session.getAttribute("loginMember") != null )) {
            MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginMember");
            nickName = memberDTO.getNickname();
        }
        responseData.put("loginMember", session.getAttribute("loginMember"));

        // Liked 테이블에서 로그인한 회원의 데이터 있는지 확인
        List<LikedEntity> likedEntityList = likedRepository.findByNickname(nickName);
        System.out.println("likedEntityList = " + likedEntityList);
        responseData.put("likedEntityList", likedEntityList);

        // 화면 좌표값에 따른 행정동 리스트
        List<HangJeongDongDTO> hjdList = aptServiceImpl.findHJDListBounds(southWestLat, southWestLng, northEastLat, northEastLng, zoomLevel);
        responseData.put("hjdList", hjdList);

        // 화면 좌표값에 따른 마커
        List<MaeMulRegDTO> maemulList = maemulRegService.findMaemulListBounds(southWestLat, southWestLng, northEastLat, northEastLng, tradeType
                , numberOfRooms, numberOfBathrooms, floorNumber, managementFee, Elevator, direction, Parking, shortTermRental, keyword, rowSellingPrice, highSellingPrice
                , rowDepositForLease, highDepositForLease);
        responseData.put("maenulList", maemulList);

        // 마커 클릭시 해당 주소의 매물 리스트 가져오기
        List<MaeMulRegDTO> maemulClickList = maemulRegService.findMaemulByAddress(address);
        responseData.put("maemulClickList", maemulClickList);

        // 검색시 해당 키워드의 매물 리스트 가져오기
        if(keyword != null) {
            List<MaeMulRegDTO> maemulKeywordList = maemulRegService.findByMaemulKeyword(keyword);
            responseData.put("maemulKeywordList", maemulKeywordList);
        }

        if(value != null){
            // 주거용 상업용 버튼 클릭시 매물 리스트 가져오기
            List<MaeMulRegDTO> maemulButtonList = maemulRegService.findByMaemulButton(value);
            responseData.put("maemulButtonList", maemulButtonList);
        }


        return ResponseEntity.ok(responseData);
    }
}