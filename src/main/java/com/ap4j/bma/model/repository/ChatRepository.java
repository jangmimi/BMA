package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

public interface ChatRepository extends JpaRepository <ChatMessage,Long> {
    @Query("SELECT c FROM ChatMessage c WHERE c.chatDate BETWEEN :entityStartTime AND CURRENT_TIMESTAMP")
    List findByDateMessages(@Param("entityStartTime") LocalDateTime entityStartTime);
}
