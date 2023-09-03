package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.customerCenter.FAQEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FAQRepository extends JpaRepository<FAQEntity, Integer> {
}
