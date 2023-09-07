package com.ap4j.bma.controller.details;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
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
import java.util.List;
import java.util.Optional;

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


	@GetMapping("/miniHome/{nickname}")
	public String miniHome(Model model, @PathVariable("nickname") String nickname) {
		log.info(">>>>> DetailController.miniHome.executed() {}", nickname);

		model.addAttribute("maemulList", maemulRegRepository.findMaemulByMemberNickname(nickname));
		model.addAttribute("residentialCount", maemulRegRepository.countResidential(nickname));
		model.addAttribute("commercialCount", maemulRegRepository.countCommercial(nickname));

		log.info("maemulList 객체 {}", maemulRegRepository.findByMemberEntity_Nickname(nickname));
		return "details/miniHome";
	}


}
