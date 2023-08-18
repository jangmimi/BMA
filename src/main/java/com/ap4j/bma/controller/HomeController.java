package com.ap4j.bma.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
public class HomeController {


	@RequestMapping("/")
	public String test(){
		log.info("HomeController.payments.execute");
		return "payments/payments";
	}

}
