package com.ap4j.bma.controller.chat;

import com.ap4j.bma.model.entity.chat.ChatMessage;
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@SessionAttributes("loginMember")
@Controller
public class ChatHandler {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/connect")
    @SendTo("/topic/chatting/connectmessage")
    public ChatMessage ConnectMessage(ChatMessage message, SimpMessageHeaderAccessor accessor) {
        MemberDTO member = (MemberDTO) accessor.getSessionAttributes().get("loginMember");
        if (member != null) {
            LocalDateTime connectTime = LocalDateTime.now(); // 현재 시간을 가져옴
            message.setChatDate(connectTime);
            message.setChatContent(member.getNickname() + "님이 입장하셨습니다");
        } else {
            message.setChatContent("연결실패");
        }
        return message;
    }

    @MessageMapping("/saveMessage")
    @SendTo("/topic/chatting")
    public ChatMessage saveMessage(ChatMessage message, SimpMessageHeaderAccessor accessor) {
        MemberDTO member = (MemberDTO) accessor.getSessionAttributes().get("loginMember");
        message.setEmail(member.getEmail());
        message.setNickname(member.getNickname());
        message = chatService.saveMessage(message);
        return message;
    }

    @MessageMapping("/loadMessages")
    public void loadMessages(ChatMessage message,SimpMessageHeaderAccessor accessor) {
        LocalDateTime connectTime = message.getChatDate();
        MemberDTO member = (MemberDTO) accessor.getSessionAttributes().get("loginMember");
        System.out.println("loadMessages 컨트롤러 시간출력 = " + connectTime);
        List<ChatMessage> messages = chatService.showMessages(connectTime);
        System.out.println(messages);
        String clientTopic = "/topic/" + member.getEmail();
        messagingTemplate.convertAndSend(clientTopic, messages);
    }
}





