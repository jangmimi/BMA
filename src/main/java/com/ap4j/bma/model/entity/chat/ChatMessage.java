package com.ap4j.bma.model.entity.chat;

import lombok.*;

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
    @Column
    private String chatContent;
    @Column
    private String chatSender;
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime chatDate;

}