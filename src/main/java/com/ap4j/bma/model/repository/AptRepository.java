package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.aptTest.AptEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AptRepository extends JpaRepository<AptEntity, Long> {
}
