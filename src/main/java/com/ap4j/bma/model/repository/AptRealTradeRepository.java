package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.apt.AptRealTradeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AptRealTradeRepository extends JpaRepository<AptRealTradeEntity, Long> {

    @Query("SELECT d FROM AptRealTradeEntity d WHERE d.roadName = ?1 order by d.contractYearMonth desc, d.contractDate desc, d.area desc")
    List<AptRealTradeEntity> findByRoadName(String roadName);
}
