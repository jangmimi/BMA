package com.ap4j.bma.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

import javax.servlet.http.HttpSession;

@SessionAttributes("loginMember")
@Slf4j
@Controller
public class HomeController {

//	@RequestMapping("/")
//	public String test(){
//		log.info("HomeController.payments.execute");
//		return "payments/payments";
//	}

	@RequestMapping("/")
	public String mainPage(HttpSession session, Model model){
		log.info("session userEmail : " + session.getAttribute("loginMember"));
		model.addAttribute("loginMember");
		return "mainPage/mainPage";
	}

}
