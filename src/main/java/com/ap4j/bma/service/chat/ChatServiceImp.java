package com.ap4j.bma.service.chat;

import com.ap4j.bma.model.entity.chat.ChatMessage;
import com.ap4j.bma.model.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImp implements ChatService{

    private final ChatRepository chatRepository;

    @Override
    public void saveMessage(ChatMessage chatMessage) {
        chatMessage.setChatDate(LocalDateTime.now()); // 현재 시간으로 설정
        System.out.println("메세지저장"+chatMessage);
        chatRepository.save(chatMessage);
    }

    @Override
    public List showMessages(LocalDateTime startTime) {
        System.out.println("showMessages 서비스클래스 시간출력 = " + startTime);
        return chatRepository.findByDateMessages(startTime);
    }

}
