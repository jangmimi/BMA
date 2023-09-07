package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.customerCenter.QnAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QnARepository extends JpaRepository<QnAEntity, Integer> {
    // 전체 게시글 개수를 조회하는 메서드 추가
    @Query("SELECT COUNT(q) FROM QnAEntity q")
    int countAllQnA();
}
