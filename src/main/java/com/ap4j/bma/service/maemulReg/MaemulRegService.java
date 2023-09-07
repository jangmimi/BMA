package com.ap4j.bma.service.maemulReg;

import com.ap4j.bma.model.entity.meamulReg.MaeMulRegDTO;
import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.repository.MaemulRegRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MaemulRegService {

    private final MaemulRegRepository maemulRegRepository;

    @Autowired
    public MaemulRegService(MaemulRegRepository maemulRegRepository) {
        this.maemulRegRepository = maemulRegRepository;
    }

    // 매물 정보 저장
    @Transactional
    public MaemulRegEntity saveMaemulInfo(MaemulRegEntity maemulRegEntity) {
        return maemulRegRepository.save(maemulRegEntity);
    }

    public MaemulRegEntity getMaemulById(Integer maemulId) {
        // 매물 ID로 매물 정보를 조회
        return maemulRegRepository.findById(maemulId).orElse(null);
    }

    // 매물 좌표값 업데이트
    public void updateMeamulReg(Integer maemulId, Double latitude, Double longitude) {
        MaemulRegEntity updateMaemul = maemulRegRepository.findById(maemulId).orElse(null);
        if (updateMaemul != null) {
            updateMaemul.setLatitude(latitude);
            updateMaemul.setLongitude(longitude);
            maemulRegRepository.save(updateMaemul);
        }
    }

    // 좌표값에 따른 매물 리스트 (마커 찍기용)
    public List<MaeMulRegDTO> findMaemulListBounds(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng) {
        List<MaeMulRegDTO> maeMulRegDTOList = new ArrayList<>();
        List<MaemulRegEntity> maemulRegEntityList = maemulRegRepository.findMaemulListBounds(southWestLat, southWestLng, northEastLat, northEastLng);

        for (MaemulRegEntity maemulRegEntity : maemulRegEntityList) {
            MaeMulRegDTO maeMulRegDTO = MaeMulRegDTO.builder()
                    .id(maemulRegEntity.getId())
                    .nickname(maemulRegEntity.getNickname())
                    .address(maemulRegEntity.getAddress())
                    .APT_name(maemulRegEntity.getAPT_name())
                    .buildingUsage(maemulRegEntity.getBuildingUsage())
                    .numberOfRooms(maemulRegEntity.getNumberOfRooms())
                    .numberOfBathrooms(maemulRegEntity.getNumberOfBathrooms())
                    .floorNumber(maemulRegEntity.getFloorNumber())
                    .totalFloors(maemulRegEntity.getTotalFloors())
                    .privateArea(maemulRegEntity.getPrivateArea())
                    .supplyArea(maemulRegEntity.getSupplyArea())
                    .direction(maemulRegEntity.getDirection())
                    .heatingType(maemulRegEntity.getHeatingType())
                    .Elevator(maemulRegEntity.getElevator())
                    .Parking(maemulRegEntity.getParking())
                    .totalParking(maemulRegEntity.getTotalParking())
                    .shortTermRental(maemulRegEntity.getShortTermRental())
                    .availableMoveInDate(maemulRegEntity.getAvailableMoveInDate())
                    .loanAmount(maemulRegEntity.getLoanAmount())
                    .tradeType(maemulRegEntity.getTradeType())
                    .monthlyForRent(maemulRegEntity.getMonthlyForRent())
                    .monthlyRent(maemulRegEntity.getMonthlyRent())
                    .depositForLease(maemulRegEntity.getDepositForLease())
                    .managementFee(maemulRegEntity.getManagementFee())
                    .SellingPrice(maemulRegEntity.getSellingPrice())
                    .title(maemulRegEntity.getTitle())
                    .content(maemulRegEntity.getContent())
                    .features(maemulRegEntity.getFeatures())
                    .optional(maemulRegEntity.getOptional())
                    .security(maemulRegEntity.getSecurity())
                    .longitude(maemulRegEntity.getLongitude())
                    .latitude(maemulRegEntity.getLatitude())
                    .createdAt(maemulRegEntity.getCreatedAt())
                    .build();
            maeMulRegDTOList.add(maeMulRegDTO);
        }
        return maeMulRegDTOList;
    }

    // 마커 클릭시 해당 주소값과 같은 매물 리스트 불러오기
    public List<MaeMulRegDTO> findMaemulByAddress(String address) {
        List<MaeMulRegDTO> maeMulRegDTOList = new ArrayList<>();
        List<MaemulRegEntity> maemulRegEntityList = maemulRegRepository.findMaemulByAddress(address);
        for (MaemulRegEntity maemulRegEntity : maemulRegEntityList) {
            MaeMulRegDTO maeMulRegDTO = MaeMulRegDTO.builder()
                    .id(maemulRegEntity.getId())
                    .nickname(maemulRegEntity.getNickname())
                    .address(maemulRegEntity.getAddress())
                    .APT_name(maemulRegEntity.getAPT_name())
                    .buildingUsage(maemulRegEntity.getBuildingUsage())
                    .numberOfRooms(maemulRegEntity.getNumberOfRooms())
                    .numberOfBathrooms(maemulRegEntity.getNumberOfBathrooms())
                    .floorNumber(maemulRegEntity.getFloorNumber())
                    .totalFloors(maemulRegEntity.getTotalFloors())
                    .privateArea(maemulRegEntity.getPrivateArea())
                    .supplyArea(maemulRegEntity.getSupplyArea())
                    .direction(maemulRegEntity.getDirection())
                    .heatingType(maemulRegEntity.getHeatingType())
                    .Elevator(maemulRegEntity.getElevator())
                    .Parking(maemulRegEntity.getParking())
                    .totalParking(maemulRegEntity.getTotalParking())
                    .shortTermRental(maemulRegEntity.getShortTermRental())
                    .availableMoveInDate(maemulRegEntity.getAvailableMoveInDate())
                    .loanAmount(maemulRegEntity.getLoanAmount())
                    .tradeType(maemulRegEntity.getTradeType())
                    .monthlyForRent(maemulRegEntity.getMonthlyForRent())
                    .monthlyRent(maemulRegEntity.getMonthlyRent())
                    .depositForLease(maemulRegEntity.getDepositForLease())
                    .managementFee(maemulRegEntity.getManagementFee())
                    .SellingPrice(maemulRegEntity.getSellingPrice())
                    .title(maemulRegEntity.getTitle())
                    .content(maemulRegEntity.getContent())
                    .features(maemulRegEntity.getFeatures())
                    .optional(maemulRegEntity.getOptional())
                    .security(maemulRegEntity.getSecurity())
                    .longitude(maemulRegEntity.getLongitude())
                    .latitude(maemulRegEntity.getLatitude())
                    .createdAt(maemulRegEntity.getCreatedAt())
                    .build();
            maeMulRegDTOList.add(maeMulRegDTO);
        }
        return maeMulRegDTOList;
    }
}