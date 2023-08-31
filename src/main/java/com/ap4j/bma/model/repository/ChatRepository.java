package com.ap4j.bma.model.repository;

import com.ap4j.bma.model.entity.chat.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatRepository extends JpaRepository <ChatMessage,Long> {

//    @Query(value = "SELECT c FROM ChatMessage c WHERE CONVERT_TZ(c.chatDate, '+00:00', '+09:00') BETWEEN :entityStartTime AND CURRENT_TIMESTAMP", nativeQuery = true)
    @Query(value = "SELECT * FROM chat_message WHERE chat_date >= ?1",nativeQuery = true)
    List findByDateMessages(LocalDateTime entityStartTime);
}
