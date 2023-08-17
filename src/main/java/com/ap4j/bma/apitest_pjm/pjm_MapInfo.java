package com.ap4j.bma.apitest_pjm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class pjm_MapInfo {
    private String name;
    private String address;
    private double x;   // 위도
    private double y;   // 경도
}
