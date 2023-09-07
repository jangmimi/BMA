package com.ap4j.bma.service.chat;

import com.ap4j.bma.model.entity.chat.ChatMessage;
import com.ap4j.bma.model.entity.chat.ChatMessageDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatService {

    public ChatMessage saveMessage(ChatMessageDTO chatMessageDTO);

    public List showMessages(LocalDateTime startTime);

}
