package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;

import java.util.List;

public interface MaemulRepositoryCustom {
    List<MaemulRegEntity> findMaemulListBounds(Double southWestLat, Double southWestLng, Double northEastLat, Double northEastLng, String tradeType
            , Integer numberOfRooms, Integer numberOfBathrooms, Integer floorNumber, Integer managementFee, String Elevator, String direction
            , String Parking, String shortTermRental, String keyword);



}
