package com.ap4j.bma.service.chat;

import com.ap4j.bma.model.entity.chat.ChatMessage;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatService {

    public ChatMessage saveMessage(ChatMessage chatMessage);

    public List showMessages(LocalDateTime startTime);

}
