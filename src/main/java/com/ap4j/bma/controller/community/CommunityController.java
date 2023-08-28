package com.ap4j.bma.controller.community;

import com.ap4j.bma.model.entity.community.CommunityEntity;
import com.ap4j.bma.service.community.CommunityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CommunityController {
    @Autowired
    private CommunityService communityService;

    //커뮤니티 리스트 출력
    @GetMapping("/community/list")
    public  String communityList(Model model){
        List<CommunityEntity> communityEntities = communityService.getAllCommunity();
        model.addAttribute("list", communityEntities);
        return "community/communityList";
   }

   //글작성
    @GetMapping("/community/write")
    public  String communityWriteForm(){

        return "community/communityWrite";
    }

    //글 작성 DB저장
    @PostMapping("/community/write")
    public String communityWrite(@ModelAttribute CommunityEntity communityEntity){
        communityService.saveCommunity(communityEntity);
        return "redirect:/community/list";
    }


    //글 상세 보기
    @GetMapping("/community/view")
    public String communityView(Model model , Integer id){

        model.addAttribute("article",communityService.communityView(id));
        return "community/communityView";
    }

    @GetMapping("/community/delete")
    public String communityDelete(Integer id){

        communityService.communityDelete(id);

        return "redirect:/community/list";
    }


//    @GetMapping("/community/modify/{id}")
//    public String communityModify(@PathVariable("id") Integer id,
//                              Model model){
//
//        model.addAttribute("board", boardService.boardView(id));
//        return "community/communityModify";
//    }

//    @PostMapping("/community/update/{id}")
//    public String communityUpdate(@PathVariable("id") Integer id, communityVO board, Model model , MultipartFile file) throws Exception{
//
//        communityVO boardTemp = boardService.boardView(id);
//        boardTemp.setTitle(board.getTitle());
//        boardTemp.setContent(board.getContent());
//
//        model.addAttribute("message","글 수정이 완료되었습니다.");
//        model.addAttribute("searchUrl", "/community/list");
//
//        boardService.boardWrite(boardTemp, file);
//
//        return "community/message";

   // }
}




