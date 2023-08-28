package com.ap4j.bma.controller.details;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("details")
@Slf4j
public class DetailController {

	@GetMapping("/")
	public String details(){
		log.info("DetailController.details.executed");

		return "details/details";
	}


}
