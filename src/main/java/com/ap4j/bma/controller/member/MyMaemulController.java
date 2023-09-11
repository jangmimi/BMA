package com.ap4j.bma.controller.member;

import com.ap4j.bma.model.entity.meamulReg.MaemulRegEntity;
import com.ap4j.bma.model.entity.member.LikedEntity;
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.service.member.LikedService;
import com.ap4j.bma.service.member.MemberService;
import com.ap4j.bma.service.member.RecentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@SessionAttributes("loginMember")
@RequestMapping("/member")
@Controller
public class MyMaemulController {
    @Autowired
    private MemberService qMemberService;

    @Autowired
    private LikedService likedService;

    @Autowired
    private RecentServiceImpl recentServiceImpl;

    /** 로그인 여부 체크 */
    public boolean loginStatus(HttpSession session) {
        return session.getAttribute("loginMember") != null;
    }

    /** 매물관리 페이지 매핑 */
    @RequestMapping("/qManagement")
    public String qManagement(@RequestParam(name = "page", defaultValue = "1") int page,
                              @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, HttpSession session, Model model) {
        if(!loginStatus(session)) { return "userView/loginNeed"; }

        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginMember");
        String nickname = memberDTO.getNickname();
        if (page < 1) {
            page = 1;
        }
        List<MaemulRegEntity> mmList = qMemberService.getListByNickname(memberDTO.getNickname());

        Page<MaemulRegEntity> mmpList = qMemberService.getPageByNickname(nickname,page,pageSize);

        model.addAttribute("mmList",mmpList);
        model.addAttribute("mmAllCnt",mmList.size());

        return "userView/maemulManagement";
    }
    /** 매물 삭제 */
    @PostMapping("/qDeleteMaemul")
    public String qDeleteMaemul(@RequestParam("id") Integer id, String nickname, HttpSession session) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginMember");
        nickname = memberDTO.getNickname();
        log.info("삭제매물id확인 : "+id);
        int result = qMemberService.deleteMaemul(id, nickname);
        log.info("삭제결과 : " + result);
        return "redirect:/member/qManagement";
    }

    /** 관심매물 등록 */
    @RequestMapping("/qLiked")
    public String qLiked(HttpSession session, Model model, Integer maemulId) {
        if(!loginStatus(session)) { return "userView/loginNeed"; }

        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginMember");
        String nickname = memberDTO.getNickname();
        MaemulRegEntity finemm = qMemberService.findMaemulById(maemulId);

        LikedEntity likedEntity = new LikedEntity();
        likedEntity.setNickname(nickname);
        likedEntity.setMaemul_id(maemulId);

        likedService.save(likedEntity);

        return "redirect:/map/map";
    }
    /** 관심매물 삭제 */
    @PostMapping("/qDeleteLiked")
    public String qDeleteLiked(@RequestParam("maemul_id") Integer maemul_id, String nickname, HttpSession session) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginMember");
        nickname = memberDTO.getNickname();
        log.info("삭제확인"+maemul_id);
        likedService.deleteByMaemulIdAndNickname(maemul_id, nickname);
        return "redirect:/member/liked";
    }

    /** 관심매물 페이지 매핑 */
    @RequestMapping("/liked")
    public String qInterest(@RequestParam(name = "page", defaultValue = "1") int page,
                            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,HttpSession session, Model model) {
        if(!loginStatus(session)) { return "userView/loginNeed"; }

        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginMember");
        String nickname = memberDTO.getNickname();

        if (page < 1) {
            page = 1;
        }

        Page<MaemulRegEntity> mmpList = likedService.getPaginatedItems(nickname,page,pageSize);
        Long totalCount = likedService.countLikedByNickname(nickname);
        model.addAttribute("totalCount",totalCount);
        model.addAttribute("mmpList",mmpList);

        return "userView/maemulLiked";
    }

    @GetMapping("/searchl")
    public String searchl(  String keyword,
                            @RequestParam(name = "page", defaultValue = "1") int page,
                            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, HttpSession session, Model model) {
        if (!loginStatus(session)) {
            return "userView/loginNeed";
        }
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginMember");
        String nickname = memberDTO.getNickname();

        if (page < 1) {
            page = 1;
        }

        Page<MaemulRegEntity> mmpList = likedService.getSearchPaginatedItems(nickname,keyword,page,pageSize);
        log.info(mmpList.toString());
        Long totalCount = likedService.countFindLikedByNickname(nickname,keyword);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("mmpList", mmpList);

        return "userView/searchMaemulLiked";
    }

    /** 최근매물 페이지 매핑 */
    @RequestMapping("/qRecent")
    public String qRecent(@RequestParam(name = "page", defaultValue = "1") int page,
                          @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,HttpSession session, Model model) {
        log.info("MemberController - qRecent() 실행");
        if(!loginStatus(session)) { return "userView/loginNeed"; }
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginMember");
        String nickname = memberDTO.getNickname();

        if (page < 1) {
            page = 1;
        }

        Page<MaemulRegEntity> mmpList = recentServiceImpl.recentMaemulList(nickname,page,pageSize);
        Long totalCount = recentServiceImpl.recentMamulListCount(nickname);

        model.addAttribute("mmpList",mmpList);
        model.addAttribute("totalCount",totalCount);

        return "userView/maemulRecent";
    }

    @GetMapping("/searchr")
    public String searchr(  String keyword,
                            @RequestParam(name = "page", defaultValue = "1") int page,
                            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize, HttpSession session, Model model) {
        if (!loginStatus(session)) {
            return "userView/loginNeed";
        }
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginMember");
        String nickname = memberDTO.getNickname();

        if (page < 1) {
            page = 1;
        }

        Page<MaemulRegEntity> mmpList = recentServiceImpl.searchRecentMaemulList(nickname,keyword,page,pageSize);
        log.info(mmpList.toString());
        Long totalCount = recentServiceImpl.searchRecentMaemulListCount(nickname,keyword);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("mmpList", mmpList);

        return "userView/searchMaemulRecent";
    }

    /** 최근본매물 삭제 */
    @PostMapping("/qDeleteRecent")
    public String qDeleteRecent(@RequestParam("maemul_id") Integer maemul_id, String nickname, HttpSession session) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginMember");
        nickname = memberDTO.getNickname();
        log.info("삭제할 최근본매물 : " + maemul_id);
        recentServiceImpl.recentDelete(maemul_id, nickname);
        return "redirect:/member/qRecent";
    }
}
