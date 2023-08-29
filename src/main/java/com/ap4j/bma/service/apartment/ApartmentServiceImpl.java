package com.ap4j.bma.service.apartment;

import com.ap4j.bma.model.entity.apt.AptDTO;

import com.ap4j.bma.model.entity.apt.AptRealTradeDTO;
import com.ap4j.bma.model.entity.apt.AptEntity;

import com.ap4j.bma.model.entity.apt.AptRealTradeEntity;
import com.ap4j.bma.model.repository.AptRealTradeRepository;
import com.ap4j.bma.model.repository.AptRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApartmentServiceImpl implements ApartmentService {

    @Autowired
    private AptRepository aptRepository;

    @Autowired
    private AptRealTradeRepository aptRealTradeRepository;

    /**
     * DB값 가져와서 js에 넘겨주기 (경도 위도 검색해서 값 가져오기 위해서)
     */
    public List<AptDTO> aptList() {
        List<AptDTO> AptDTOList = new ArrayList<>();
        List<AptEntity> aptEntityList = aptRepository.findAll();

        for (AptEntity aptEntity : aptEntityList) {
            AptDTO aptDTO = AptDTO.builder().
                    complexName(aptEntity.getComplexName()).
                    roadName(aptEntity.getRoadName()).
                    district(aptEntity.getDistrict()).
                    address(aptEntity.getAddress()).
                    build();
            AptDTOList.add(aptDTO);
        }
        return AptDTOList;
    }

    /**
     * 가져온 좌표값 DB에 저장하기
     */
    @Transactional
    public void updateApt(String roadName, Double latitude, Double longitude) {
        AptEntity aptEntity = aptRepository.findByRoadName(roadName);
        if (aptEntity != null) {
            AptEntity updateApt = AptEntity.builder().
                            id(aptEntity.getId()).
                            district(aptEntity.getDistrict()).
                            address(aptEntity.getAddress()).
                            complexName(aptEntity.getComplexName()).
                            roadName(aptEntity.getRoadName()).
                            latitude(latitude).
                            longitude(longitude).
                            build();
            aptRepository.save(updateApt);
        }
    }

    /**
     * ajax통신으로 가져온 화면의 좌표 범위의 데이터만 가져오는 메서드
     */
    public List<AptDTO> findAptListBounds(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng) {

        List<AptDTO> aptDTOList = new ArrayList<>();
        List<AptEntity> aptEntityList = aptRepository.findAptListBounds(southWestLat, southWestLng, northEastLat, northEastLng);

        for (AptEntity aptEntity : aptEntityList) {
            AptDTO aptDTO = AptDTO.builder().
                    complexName(aptEntity.getComplexName()).
                    district(aptEntity.getDistrict()).
                    address(aptEntity.getAddress()).
                    roadName(aptEntity.getRoadName()).
                    longitude(aptEntity.getLongitude()).
                    latitude(aptEntity.getLatitude()).
                    build();
            aptDTOList.add(aptDTO);
        }
        return aptDTOList;
    }

    /**
     * 도로명에 맞는 실거래가 데이터 가져오는 메서드
     */
    public List<AptRealTradeDTO> findByRoadName(String roadName) {
        List<AptRealTradeDTO> aptRealTradeDTOList = new ArrayList<>();
        List<AptRealTradeEntity> aptRealTradeEntityList = aptRealTradeRepository.findByRoadName(roadName);

        for (AptRealTradeEntity aptRealTradeEntity : aptRealTradeEntityList) {
            AptRealTradeDTO aptRealTradeDTO = AptRealTradeDTO.builder().
                    complexName(aptRealTradeEntity.getComplexName()).
                    district(aptRealTradeEntity.getDistrict()).
                    address(aptRealTradeEntity.getAddress()).
                    roadName(aptRealTradeEntity.getRoadName()).
                    area(aptRealTradeEntity.getArea()).
                    transactionAmount(aptRealTradeEntity.getTransactionAmount()).
                    contractYearMonth(aptRealTradeEntity.getContractYearMonth()).
                    contractDate(aptRealTradeEntity.getContractDate()).
                    floor(aptRealTradeEntity.getFloor()).
                    constructionYear(aptRealTradeEntity.getConstructionYear()).
                    build();
            aptRealTradeDTOList.add(aptRealTradeDTO);
        }
        return aptRealTradeDTOList;
    }

    /**
     * 아파트명 또는 도로명으로 검색시 아파트 정보 가져오기
     */
//    public AptDTO findByKeyword(String keyword) {
//        AptEntity aptEntity = aptRepository.findByKeyword(keyword);
//        AptDTO aptKeyword = AptDTO.builder().
//
//                build()
//    }
}
