package com.ap4j.bma.model.entity.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatConnectRequest {

    private String userId;
    private LocalDateTime connectedTime;
    public String getConnectedTimeAsString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return connectedTime.format(formatter);
    }

    public ChatMessage toChatMessage(){
        return new ChatMessage(null,null,this.userId,this.connectedTime);
    }

}
