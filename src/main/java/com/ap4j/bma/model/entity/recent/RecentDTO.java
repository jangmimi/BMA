package com.ap4j.bma.model.entity.recent;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class RecentDTO {

    private Integer id; // RecentEntity의 ID
    private Integer maemulId; // 연결된 매물의 ID
    private String memberNickname; // 연결된 회원의 닉네임

}
