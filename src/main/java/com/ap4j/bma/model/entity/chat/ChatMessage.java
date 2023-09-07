package com.ap4j.bma.model.entity.chat;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @CreationTimestamp
    private LocalDateTime chatDate;

    @Column
    private String chatContent;

    @Column
    private String nickname;

    public ChatMessageDTO toDTO(){
        return new ChatMessageDTO(this.chatId,this.chatDate,this.chatContent,this.nickname);
    }

}