package com.ap4j.bma.controller.details;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.entity.recent.RecentDTO;
import com.ap4j.bma.model.repository.MaemulRegEntityRepository;
import com.ap4j.bma.model.repository.MemberRepository;
import com.ap4j.bma.model.repository.RecentRepository;
import com.ap4j.bma.service.maemulReg.MaemulRegService;
import com.ap4j.bma.service.member.RecentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("details")
@SessionAttributes("loginMember")
@Slf4j
public class DetailController {


	private final MaemulRegEntityRepository maemulRegRepository;
	private final MaemulRegService maemulRegService;
	private final RecentRepository recentRepository;
	private final MemberRepository memberRepository;
	private final RecentDTO recentDTO;
	private final RecentServiceImpl recentService;

	@Autowired
	public DetailController(MaemulRegEntityRepository maemulRegRepository, MaemulRegService maemulRegService, RecentRepository recentRepository, MemberRepository memberRepository, RecentDTO recentDTO, RecentServiceImpl recentService) {
		this.maemulRegRepository = maemulRegRepository;
		this.maemulRegService = maemulRegService;
		this.recentRepository = recentRepository;
		this.memberRepository = memberRepository;
		this.recentDTO = recentDTO;
		this.recentService = recentService;
	}



	@GetMapping("/{id}")
	public String details(
			Model model,
			@PathVariable("id") int id,
			@RequestParam("nickname") String nickname
	) {
		log.info("DetailController.details.executed");

		MaemulRegEntity maemul = maemulRegRepository.findById(id).orElse(null);

		// 로그인 한 회원일 경우에만 최근 본 매물 테이블에 등록.
		if(nickname != null && !nickname.equals("null")) {
			recentService.recentCheck(id, nickname);
		}

		model.addAttribute("maemulList", maemul);
		log.info("maemulList 객체 {}", maemul);
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
