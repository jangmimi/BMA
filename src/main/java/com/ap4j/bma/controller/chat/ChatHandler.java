package com.ap4j.bma.controller.chat;

import com.ap4j.bma.model.entity.chat.ChatMessage;
import com.ap4j.bma.model.entity.chat.ChatMessageDTO;
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@SessionAttributes("loginMember")
@Controller
public class ChatHandler {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;
    private final Set<String> connectedUsers = new HashSet<>(); // 연결된 사용자 목록

    @MessageMapping("/connect")
    @SendTo("/topic/chatting/connectmessage")
    public ChatMessageDTO ConnectMessage(ChatMessageDTO messageDTO, SimpMessageHeaderAccessor accessor) {
        //세션 정보를 인터셉터
        MemberDTO member = (MemberDTO) accessor.getSessionAttributes().get("loginMember");
        //로그인이 안되어있다면 출력되지 않는 입장메세지
        if (member != null) {
            messageDTO.setChatContent(member.getNickname() + "님이 입장하셨습니다");
            messageDTO.setChatDate(LocalDateTime.now());
            // 새로 연결된 사용자를 connectedUsers 목록에 추가
            connectedUsers.add(member.getNickname());

            // 연결된 사용자 수를 클라이언트에 전송
            sendConnectedUsersCount();

        } else {
            messageDTO.setChatContent("연결실패");
        }
        return messageDTO;
    }

    private void sendConnectedUsersCount() {
        // 연결된 사용자 수를 전체 채팅방에 알리는 메시지 생성
        ChatMessageDTO userCountMessage = new ChatMessageDTO();
        userCountMessage.setChatContent(String.valueOf(connectedUsers.size()));

        System.out.println("핸들러 사용자수 "+userCountMessage);
        // 전체 채팅방에 메시지 전송
        messagingTemplate.convertAndSend("/topic/chatting/status", userCountMessage);
    }

    // 연결이 끊어진 사용자 처리
    @EventListener
    public void handleDisconnectEvent(SessionDisconnectEvent event) {
        // 사용자 세션에서 email 정보를 가져와 connectedUsers 목록에서 제거
        SimpMessageHeaderAccessor headerAccessor = SimpMessageHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();
        MemberDTO member = (MemberDTO) headerAccessor.getSessionAttributes().get("loginMember");
        if (member != null) {
            connectedUsers.remove(member.getNickname());
            // 연결된 사용자 수를 다시 전송
            sendConnectedUsersCount();
        }
    }

    @MessageMapping("/saveMessage")
    @SendTo("/topic/chatting")
    public ChatMessageDTO saveMessage(ChatMessageDTO message, SimpMessageHeaderAccessor accessor) {
        MemberDTO member = (MemberDTO) accessor.getSessionAttributes().get("loginMember");
        message.setNickname(member.getNickname());
        message = chatService.saveMessage(message).toDTO();
        return message;
    }

    @MessageMapping("/loadMessages")
    public void loadMessages(ChatMessageDTO message,SimpMessageHeaderAccessor accessor) {
        MemberDTO member = (MemberDTO) accessor.getSessionAttributes().get("loginMember");
        List<ChatMessage> messages = chatService.showMessages(message.getChatDate());
        String clientTopic = "/topic/" + member.getEmail();
        messagingTemplate.convertAndSend(clientTopic, messages);
    }
}





