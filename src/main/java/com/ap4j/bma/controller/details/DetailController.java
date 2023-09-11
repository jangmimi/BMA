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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public String details(
			Model model,
			@PathVariable("id") int id,
			@RequestParam(value = "nickname", required = false) String nickname,
			HttpSession session
	) {
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

	/** 좋아요 버튼 적용 메서드 details.js 에서 ajax 로 불러옴. */
	@RequestMapping("/like")
	@ResponseBody
	public Map<String, Object> liked(@RequestParam("id") int id, @RequestParam("nickname") String nickname) {
		log.info(">>>>> DetailController.liked.executed()");

//		// db에서 좋아요 정보를 확인하는 로직을 추가
//		boolean isLiked = likedService.isLiked(id, nickname);
//
//		// 만약 이미 좋아요를 했을 경우, 클라이언트에게 바로 알려줄 수 있습니다.
//		if (isLiked) {
//			Map<String, Object> response = new HashMap<>();
//			response.put("liked", true); // 이미 좋아요를 한 경우 true를 클라이언트에게 전달
//			return response;
//		}

		LikedEntity likedEntity = new LikedEntity();
		likedEntity.setNickname(nickname);
		likedEntity.setMaemul_id(id);

		likedService.save(likedEntity);

		Map<String, Object> response = new HashMap<>();
		response.put("liked", true); // 좋아요 추가 후 클라이언트에게 true를 전달
		return response;
	}

//	@RequestMapping("/like")
//	public ResponseEntity<String> like(int id, String nickname) {
//		// 처리할 백엔드 로직을 여기에 작성합니다.
//		log.info(">>>>> DetailController.like.executed()");
//		List<LikedEntity> likedEntities = likedRepository.findByNickname(nickname);
//		log.info("좋아요 한 여부{}", likedEntities);
//
//		LikedEntity likedEntity = new LikedEntity();
//		likedEntity.setNickname(nickname);
//		likedEntity.setMaemul_id(id);
//
//		likedService.save(likedEntity);
//		// 로직 실행 후 응답을 반환합니다.
//		return ResponseEntity.ok("Like request processed successfully.");
//	}


}
