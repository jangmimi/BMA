package com.ap4j.bma.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.SessionAttribute;
>>>>>>> d0e7fac4452d7c81530b2201e99ba80582b9db7c
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.SessionScope;

<<<<<<< HEAD
import javax.servlet.http.HttpSession;

=======
>>>>>>> d0e7fac4452d7c81530b2201e99ba80582b9db7c
@SessionAttributes("userEmail")
@Slf4j
@Controller
public class HomeController {


//	@RequestMapping("/")
//	public String test(){
//		log.info("HomeController.payments.execute");
//		return "payments/payments";
//	}

	@RequestMapping("/")
	public String mainPage(){
		return "mainPage/mainPage";
	}

}
