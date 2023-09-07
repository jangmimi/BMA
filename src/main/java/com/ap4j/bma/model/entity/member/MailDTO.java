package com.ap4j.bma.model.entity.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO {
    private String email;
    private String title;
    private String msg;
}
