package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.customerCenter.FAQEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FAQRepository extends JpaRepository<FAQEntity, Integer> {

    @Query("SELECT a FROM FAQEntity a WHERE a.category = ?1")
    Page<FAQEntity> findByCategory(String category, Pageable pageable);
}
