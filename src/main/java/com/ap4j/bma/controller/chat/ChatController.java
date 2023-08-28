package com.ap4j.bma.controller.chat;

import com.ap4j.bma.model.entity.chat.ChatConnectRequest;
import com.ap4j.bma.model.entity.chat.ChatMessage;
import com.ap4j.bma.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Controller
@SessionAttributes("userEmail")
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @GetMapping("/chat") // 추가: /chat 경로로 접근할 때 chat. html을 보여줌
    public String chat(Model model) {
        return "/chat/chat";
    }

    //클라이언트단에서 MessageMapping 주소로 컨트롤러로 보냄
    //controller메서드로 message다듬고
    //SendTo 주소로 클라이언트로 보낸다
    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        System.out.println("controller테스트"+chatMessage);
        chatService.saveMessage(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/connect")
    @SendTo("/topic/openingComment")
    public String openingComment(ChatConnectRequest request){
        System.out.println("openingComment = " + request);
        chatService.saveMessage(request.toChatMessage());
        String userId = request.getUserId();
        return userId+"님이 입장하셨습니다.";
    }

    @MessageMapping("/loadMessages")
    public void loadMessages(ChatConnectRequest request){
        String connectedTimeStr = request.getConnectedTimeAsString();
        LocalDateTime connectedTime = LocalDateTime.parse(connectedTimeStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        List<ChatMessage> messages = chatService.showMessages(connectedTime);
        String clientTopic = "/user/" + request.getUserId() + "/topic/chat";
        messages.forEach(message -> messagingTemplate.convertAndSend(clientTopic, message));
    }



}
