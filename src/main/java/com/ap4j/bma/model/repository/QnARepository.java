package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.customerCenter.QnAEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnARepository extends JpaRepository<QnAEntity, Integer> {
}
