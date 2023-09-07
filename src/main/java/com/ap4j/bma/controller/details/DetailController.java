package com.ap4j.bma.controller.details;

import com.ap4j.bma.model.repository.MaemulRegEntityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.websocket.server.PathParam;

@Controller
@RequestMapping("details")
@SessionAttributes("loginMember")
@Slf4j
public class DetailController {


	private MaemulRegEntityRepository maemulRegRepository;

	@Autowired
	public DetailController(MaemulRegEntityRepository maemulRegRepository){
		this.maemulRegRepository = maemulRegRepository;
	}



	@GetMapping("/{id}")
	public String details(Model model, @PathVariable("id") int id){
		log.info("DetailController.details.executed");

		model.addAttribute("maemulList", maemulRegRepository.findById(id));
		log.info("maemulList 객체 {}", maemulRegRepository.findById(id));
		return "details/details";
	}

	
	@GetMapping("/miniHome")
	public String test(){
	    log.info(">>>>> DetailController.test.executed()");
	    return "details/miniHome";
	}


}
