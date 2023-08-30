package com.ap4j.bma.controller.community;

import com.ap4j.bma.model.entity.community.CommunityEntity;
import com.ap4j.bma.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//import java.util.List;

@Controller
public class CommunityController {
    @Autowired
    private CommunityService communityService;

    //커뮤니티 리스트 출력 + 검색 기능
    @GetMapping("/community/list")
    public String communityList(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        int pageSize = 10; // 한 페이지당 보여줄 게시글 개수
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").descending());
        Page<CommunityEntity> communityPage = communityService.getCommunityPage(pageable);

        model.addAttribute("list", communityPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", communityPage.getTotalPages());

        Long totalCommunityCount = communityService.updateTotalCommunityCount();
        model.addAttribute("totalCommunityCount", totalCommunityCount); // 총 게시글 개수 추가

        return "community/communityList";
    }

    //커뮤니티 리스트 검색 결과 페이지
    @GetMapping("/community/list/search")
    public String communitySearchList(Model model,
                                      @RequestParam(name = "page", defaultValue = "1") int page,
                                      @RequestParam(value = "keyword", required = false) String keyword) {
        int pageSize = 10; // 한 페이지당 보여줄 게시글 개수
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").descending()); //내림차순 정렬, 최근 등록된 게시물 순

        Page<CommunityEntity> communityPage;

        if (keyword != null && !keyword.isEmpty()) {
            communityPage = communityService.searchByTitle(keyword, page, pageSize);
            model.addAttribute("searchKeyword", keyword);
        } else {
            // 검색어가 없을 경우 전체 리스트를 가져오도록 수정
            communityPage = communityService.getCommunityPage(pageable);
        }

        model.addAttribute("list", communityPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", communityPage.getTotalPages());

        return "community/communitySearchList";
    }

    //글작성
    @GetMapping("/community/write")
    public String communityWriteForm() {

        return "community/communityWrite";
    }

    //글 작성 DB저장
    @PostMapping("/community/write")
    public String communityWrite(@ModelAttribute CommunityEntity communityEntity, Model model) {
        communityService.saveCommunity(communityEntity);

        model.addAttribute("message", "글 등록이 완료되었습니다.");
        model.addAttribute("searchUrl", "/community/list");

        return "community/message";

    }


    //글 상세 보기
    @GetMapping("/community/view")
    public String communityView(Model model, Integer id) {

        model.addAttribute("article", communityService.communityView(id));
        model.addAttribute("prevArticle",  communityService.getPreArticle(id));
        model.addAttribute("nextArticle",  communityService.getNextArticle(id));
        return "community/communityView";
    }


    //게시글 삭제
    @GetMapping("/community/delete")
    public String communityDelete(Integer id) {

        communityService.communityDelete(id);

        return "redirect:/community/list";
    }


    //게시글 업로드
    @GetMapping("/community/modify/{id}")
    public String communityModify(@PathVariable("id") Integer id, Model model) {

        model.addAttribute("update", communityService.communityView(id));
        return "community/communityModify";
    }

    @PostMapping("/community/update/{id}")
    public String communityUpdate(@PathVariable("id") Integer id, CommunityEntity communityEntity, Model model) throws Exception {

        CommunityEntity communityTemp = communityService.communityView(id);
        communityTemp.setTitle(communityEntity.getTitle());
        communityTemp.setContent(communityEntity.getContent());

        communityService.saveCommunity(communityTemp);

        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/community/list");

        return "community/message";

    }
}




