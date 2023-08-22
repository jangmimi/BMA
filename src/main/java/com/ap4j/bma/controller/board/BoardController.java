package com.ap4j.bma.controller.board;

import com.ap4j.bma.model.entity.board.BoardVO;
import com.ap4j.bma.service.board.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")
    public  String boardWriteForm(){

        return "board/freeBoard/boardWrite";
    }
    @PostMapping("/board/writepro")
    public String boardWritePro(BoardVO board, Model model , MultipartFile file) throws Exception{

        boardService.boardWrite(board, file);
        model.addAttribute("message","글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "list");

        return "board/freeBoard/message";
    }

    @GetMapping("/board/list")
    public  String boardList(Model model , @PageableDefault(page = 0, size =10, sort ="id", direction = Sort.Direction.DESC) Pageable pageable){

        Page<BoardVO> list = boardService.boardList(pageable);

        int nowPage = list.getPageable().getPageNumber();
        int startPage = nowPage - 4;
        int endPage = nowPage + 5;
        model.addAttribute("List", boardService.boardList(pageable));

        return "board/freeBoard/boardList";
    }

    @GetMapping("/board/view") //localhost:8082/board/view?id=1...
    public String boardView(Model model , Integer id){

        model.addAttribute("board" , boardService.boardView(id));
        return "board/freeBoard/boardView";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){

        boardService.boardDelete(id);

        return "redirect:/board/list";
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id,
                              Model model){

        model.addAttribute("board", boardService.boardView(id));
        return "board/freeBoard/boardModify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, BoardVO board, Model model , MultipartFile file) throws Exception{

        BoardVO boardTemp = boardService.boardView(id);
        boardTemp.setTitle(board.getTitle());
        boardTemp.setContent(board.getContent());

        model.addAttribute("message","글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        boardService.boardWrite(boardTemp, file);

        return "board/freeBoard/message";

    }
}




