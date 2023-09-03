package com.ap4j.bma.service.chat;

import com.ap4j.bma.model.entity.chat.ChatMessage;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ChatService {

    public ChatMessage saveMessage(ChatMessage chatMessage);

    public List showMessages(LocalDateTime startTime);

}
