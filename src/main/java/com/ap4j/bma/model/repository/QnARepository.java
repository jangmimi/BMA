package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.customerCenter.QnAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
<<<<<<< HEAD

public interface QnARepository extends JpaRepository<QnAEntity, Integer> {
    // 전체 게시글 개수를 조회하는 메서드 추가
    @Query("SELECT COUNT(q) FROM QnAEntity q")
    int countAllQnA();
=======
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QnARepository extends JpaRepository<QnAEntity, Integer> {

    /** 로그인한 멤버 email이랑 매치되는 qna 리스트 불러오기 */
    @Query("SELECT mr FROM QnAEntity mr " +
            "JOIN MemberEntity m ON mr.user_email = m.email " +
            "WHERE m.email = :user_email")
    List<QnAEntity> findMaemulByMemberEmail(@Param("user_email") String email);
>>>>>>> 1fb67e0993e61ebf389c27bb56c0204c842beb17
}
