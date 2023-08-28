package com.ap4j.bma.service.chat;

import com.ap4j.bma.model.entity.chat.ChatMessage;
import com.ap4j.bma.model.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class ChatServiceImp implements ChatService{

    private final ChatRepository chatRepository;

    @Override
    public void saveMessage(ChatMessage chatMessage) {
        chatMessage.setChatDate(LocalDateTime.now()); // 현재 시간으로 설정
        chatRepository.save(chatMessage);
    }

    @Override
    public List showMessages(LocalDateTime startTime) {
        return chatRepository.findByDateMessages(startTime);
    }

}
