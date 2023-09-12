package com.ap4j.bma.controller.details;

import com.ap4j.bma.controller.member.MemberController;
import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.entity.member.LikedEntity;
import com.ap4j.bma.model.entity.member.MemberEntity;
import com.ap4j.bma.model.entity.recent.RecentDTO;
import com.ap4j.bma.model.repository.LikedRepository;
import com.ap4j.bma.model.repository.MaemulRegEntityRepository;
import com.ap4j.bma.model.repository.MemberRepository;
import com.ap4j.bma.model.repository.RecentRepository;
import com.ap4j.bma.service.maemulReg.MaemulRegService;
import com.ap4j.bma.service.member.LikedService;
import com.ap4j.bma.service.member.RecentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
	private final LikedRepository likedRepository;
	private final LikedService likedService;

	@Autowired
	public DetailController(MaemulRegEntityRepository maemulRegRepository, MaemulRegService maemulRegService, RecentRepository recentRepository, MemberRepository memberRepository, RecentDTO recentDTO, RecentServiceImpl recentService, MemberController memberController, LikedRepository likedRepository, LikedService likedService) {
		this.maemulRegRepository = maemulRegRepository;
		this.maemulRegService = maemulRegService;
		this.recentRepository = recentRepository;
		this.memberRepository = memberRepository;
		this.recentDTO = recentDTO;
		this.recentService = recentService;
		this.memberController = memberController;
		this.likedRepository = likedRepository;
		this.likedService = likedService;
	}


	@GetMapping("/{id}")
	public String details(Model model, @PathVariable("id") int id, @RequestParam(value = "nickname", required = false) String nickname, HttpSession session) {
		log.info("DetailController.details.executed");

		MaemulRegEntity maemul = maemulRegRepository.findById(id).orElse(null);
		MemberEntity member = memberRepository.findMemberByNickname(nickname).orElse(null);
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
		model.addAttribute("member", member);

		log.info("로그인한 유저 {}", memberRepository.findMemberByNickname(nickname));
		return "details/details";
	}


	@GetMapping("/miniHome/{nickname}")
	public String miniHome(Model model, @PathVariable("nickname") String nickname) {
		log.info(">>>>> DetailController.miniHome.executed() {}", nickname);

		model.addAttribute("maemulList", maemulRegRepository.findMaemulByMemberNickname(nickname));
		model.addAttribute("residentialCount", maemulRegRepository.countResidential(nickname));
		model.addAttribute("commercialCount", maemulRegRepository.countCommercial(nickname));

		return "details/miniHome";
	}

	/**
	 * 좋아요 버튼 적용 메서드 details.js 에서 ajax 로 불러옴.
	 */
	@PostMapping("/like")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> liked(@RequestParam("id") int id, @RequestParam("nickname") String nickname) {
		log.info(">>>>> DetailController.liked.executed()");

		try {
			// 좋아요가 없는 경우 새로운 좋아요 추가
			LikedEntity likedEntity = new LikedEntity();
			likedEntity.setNickname(nickname);
			likedEntity.setMaemul_id(id);
			likedService.save(likedEntity);

			Map<String, Object> response = new HashMap<>();
			response.put("liked", true); // 좋아요 추가 후 클라이언트에게 true를 전달
			log.info("true 돌려줌{}", response);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			// 예외 발생 시 500 오류 반환
			e.printStackTrace();
			log.info("DetailController 에서 liked 캐치문 발동.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/like")
	public ResponseEntity<Map<String, Object>> getLike(@RequestParam("id") Long id, @RequestParam("nickname") String nickname) {
		log.info(">>>>> DetailController.getLike.executed()");
		log.info("id 값 {}, nickname 깂 {}", id, nickname);

		try {
			Optional<LikedEntity> isLiked = likedService.isLiked(nickname, id);
			log.info("isLiked 값{}",isLiked);
			if (isLiked.isPresent()) {
				Map<String, Object> response = new HashMap<>();
				response.put("liked", true);
				log.info("getLike 실행 결과 : True");
				return ResponseEntity.ok(response);
			} else{
				Map<String, Object> response = new HashMap<>();
				response.put("liked", false);
				log.info("getLike 실행 결과 : False");
				return ResponseEntity.ok(response);
			}
		} catch (Exception e) {
			// 예외 발생 시 500 오류 반환
			e.printStackTrace();
			log.info("DetailController 에서 liked 캐치문 발동.");

		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}


}
