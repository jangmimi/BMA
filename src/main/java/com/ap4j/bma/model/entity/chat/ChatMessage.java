package com.ap4j.bma.model.entity.chat;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

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
    private String chatClientId;
    @Column
    private String chatContent;
    @Column
    private String chatSender;
    @Column
    private LocalDateTime chatDate;

    @JsonCreator
    public ChatMessage(@JsonProperty("messageText") String chatContent, @JsonProperty("chatDate") String chatDate) {
        this.chatContent = chatContent;
        this.chatDate = LocalDateTime.parse(chatDate);
    }
}