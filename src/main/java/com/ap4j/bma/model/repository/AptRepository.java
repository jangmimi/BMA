package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.aptTest.AptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface AptRepository extends JpaRepository<AptEntity, Long> {
    AptEntity findByAptName(String aptName);

}
