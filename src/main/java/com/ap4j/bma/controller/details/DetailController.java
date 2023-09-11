package com.ap4j.bma.controller.details;

import com.ap4j.bma.controller.member.MemberController;
import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.entity.member.MemberEntity;
import com.ap4j.bma.model.entity.recent.RecentDTO;
import com.ap4j.bma.model.repository.MaemulRegEntityRepository;
import com.ap4j.bma.model.repository.MemberRepository;
import com.ap4j.bma.model.repository.RecentRepository;
import com.ap4j.bma.service.maemulReg.MaemulRegService;
import com.ap4j.bma.service.member.RecentServiceImpl;
import com.mysql.cj.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
	private final MemberController memberController;

	@Autowired
	public DetailController(MaemulRegEntityRepository maemulRegRepository, MaemulRegService maemulRegService, RecentRepository recentRepository, MemberRepository memberRepository, RecentDTO recentDTO, RecentServiceImpl recentService, MemberController memberController) {
		this.maemulRegRepository = maemulRegRepository;
		this.maemulRegService = maemulRegService;
		this.recentRepository = recentRepository;
		this.memberRepository = memberRepository;
		this.recentDTO = recentDTO;
		this.recentService = recentService;
		this.memberController = memberController;
	}



	@GetMapping("/{id}")
	public String details(
			Model model,
			@PathVariable("id") int id,
			@RequestParam(value = "nickname", required = false) String nickname,
			HttpSession session
	) {
		log.info("DetailController.details.executed");

		MaemulRegEntity maemul = maemulRegRepository.findById(id).orElse(null);

		// 로그인 상태 확인
		Boolean isLoggedIn = memberController.loginStatus(session);

		if (isLoggedIn && nickname != null && !nickname.equals("null")) {
			// 로그인한 경우, 로그인한 사용자의 nickname 사용
			log.info("User is logged in with nickname: {}", nickname);
			recentService.recentCheck(id, nickname);
			log.info("최근 본 매물 등록 완료");

		} else {
			// 로그인하지 않은 경우 또는 nickname이 없는 경우
			nickname = null;
			log.info("User is not logged in.");
		}

		maemulRegService.incrementViewCount(id);

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
