package com.ap4j.bma.controller.community;

import com.ap4j.bma.model.entity.community.CommunityCommentEntity;
import com.ap4j.bma.model.entity.community.CommunityEntity;
import com.ap4j.bma.model.entity.member.MemberDTO;
import com.ap4j.bma.model.repository.CommunityRepository;
import com.ap4j.bma.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;
//import java.util.List;

@SessionAttributes("loginMember")
@Controller
public class CommunityController {
    @Autowired
    private CommunityService communityService;
    @Autowired
    private CommunityRepository communityRepository;


    //글작성(Create)
    @GetMapping("/community/write")
    public String communityWriteForm(HttpSession session) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute("loginMember");
        if(memberDTO == null){
            return "userView/loginNeed";
        }
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

    // 글 상세 보기(Read)
    @GetMapping("/community/view")
    public String communityView(Model model, Integer id, HttpSession session) {
        // 게시글 ID를 세션에 저장
        Set<Integer> viewedArticles = (Set<Integer>) session.getAttribute("viewedArticles");

        if (viewedArticles == null) {
            viewedArticles = new HashSet<>();
            session.setAttribute("viewedArticles", viewedArticles);
        }

        // 게시글이 이미 조회되었는지 확인
        if (!viewedArticles.contains(id)) {
            // 게시글이 처음 조회되었으므로 조회수 증가 처리
            CommunityEntity communityEntity = communityService.communityView(id);

            if (communityEntity == null) {
                // 해당 ID의 게시글이 존재하지 않는 경우 예외 처리 또는 에러 페이지로 이동
                return "community/error"; // 에러 페이지로 이동하도록 수정
            }

            // 조회수 증가 후 세션에 해당 게시물 ID 저장
            communityService.updateViewCount(id);
            viewedArticles.add(id);
        }

        // 게시글 조회
        CommunityEntity communityEntity = communityService.communityView(id);
        model.addAttribute("comment", communityService.communityCommentEntity(id));
        model.addAttribute("article", communityEntity);
        model.addAttribute("prevArticle", communityService.getPreArticle(id));
        model.addAttribute("nextArticle", communityService.getNextArticle(id));

        // 이미 조회한 게시글인 경우에도 조회 가능(조회수 증가 없음)
        return "community/communityView";
    }

    //게시글 업로드(Update)
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

    //게시글 삭제(Delete)
    @GetMapping("/community/delete")
    public String communityDelete(Integer id) {

        communityService.communityDelete(id);

        return "redirect:/community/list";
    }

    // 커뮤니티 리스트 출력 및 검색
    @GetMapping("/community/list")
    public String communityList(Model model,
                                @RequestParam(name = "page", defaultValue = "1") int page,
                                @RequestParam(name = "searchType", required = false) String searchType,
                                @RequestParam(name = "keyword", required = false) String keyword,
                                @RequestParam(name = "sortType", defaultValue = "new") String sortType) {

        int pageSize = 10; // 한 페이지당 보여줄 게시글 개수
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").descending());

        switch (sortType) {
            case "old":
                pageable = PageRequest.of(page - 1, pageSize, Sort.by("createdAt").ascending());
                break;
            case "view":
                pageable = PageRequest.of(page - 1, pageSize, Sort.by("view").descending());
                break;
            default:
                pageable = PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending());
        }

        Long totalCommunityCount;
        Page<CommunityEntity> communityPage;

        if (searchType != null && keyword != null && !keyword.isEmpty()) {
            // 검색 조건이 존재하는 경우
            switch (searchType) {
                case "title":
                    communityPage = communityService.searchByTitle(keyword, page, pageSize);
                    break;
                case "content":
                    communityPage = communityService.searchByContent(keyword, page, pageSize);
                    break;
                case "author":
                    communityPage = communityService.searchByAuthor(keyword, page, pageSize);
                    break;
                case "all": // "전체" 옵션 추가
                    communityPage = communityService.searchByAll(keyword, page, pageSize);
                    break;
                default:
                    communityPage = communityService.getCommunityPage(pageable);
            }
            totalCommunityCount = communityPage.getTotalElements(); // 검색 결과 개수
        } else {
            // 검색 조건이 없는 경우
            communityPage = communityService.getCommunityPage(pageable);
            totalCommunityCount = communityService.updateTotalCommunityCount();
        }

        model.addAttribute("list", communityPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", communityPage.getTotalPages());
        model.addAttribute("totalCommunityCount", totalCommunityCount);
        model.addAttribute("sortType", sortType);

        return "community/communityList";
    }


    //댓글작성
    @PostMapping("/community/comment")
    public String commentWrite(@ModelAttribute("communityComment") CommunityCommentEntity communityCommentEntity, Integer articleId,
                               HttpSession session, Model model) {
        model.addAttribute("loginMember", session.getAttribute("loginMember"));
        CommunityEntity communityEntity = communityService.communityView(articleId); // 커뮤니티 조회 메소드를 호출하여 커뮤니티 엔티티를 가져옴
        communityCommentEntity.setCommunityEntity(communityEntity); // communityEntity 필드 설정
        communityService.CommentWrite(communityCommentEntity);

        return "redirect:/community/view?id=" + articleId;
    }

    //댓글삭제
    @PostMapping("/comment/delete")
    public String commentDelete(Integer id, String a, Integer articleId, Model model) {
        System.out.println("코멘트아이디" + id + " 아티클아이디" + articleId);
        communityService.communityCommentDelete(id);
        return "redirect:/community/view?id=" + articleId;
    }

}




