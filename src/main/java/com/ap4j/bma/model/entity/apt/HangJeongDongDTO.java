package com.ap4j.bma.model.entity.apt;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HangJeongDongDTO {
    private String siDo;
    private String siGunGu;
    private String eupMyeonDong;
    private String eupMyeonRiDong;
    private Double longitude;
    private Double latitude;
}
