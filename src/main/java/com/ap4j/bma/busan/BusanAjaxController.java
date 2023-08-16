package com.ap4j.bma.busan;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BusanAjaxController {
    @GetMapping("kim_ajaxc")
    public String mapPage(){
        return "busan/busanAjax";
    }

}
