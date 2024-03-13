package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.repository.BoardRepository;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;


    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }


    // 게시글 작성
    @PostMapping("/save")  // Getmapping("/save")하면 중복된 주소 및 URL을 사용하기 때문에 Ambiguous Error 발생
    public String save(@ModelAttribute BoardDTO boardDTO) {
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO); // boardService 클래스의 save 메서드 실행하여 글쓰기 실행
        return "index";

    }

    // 전체 게시글 목록 조회
    @GetMapping("/")
    public String findAll(Model model) {
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }


    // 글 제목을 클릭하면 글 번호가 넘어가도록 주소 설정해서 게시글 상세 조회
    @GetMapping("/{id}") // 경로상에 있는 값을 가져올 때에는 @PathVariable 애노테이션 사용
    public String findById(@PathVariable Long id, Model model) {
        /*
        게시글 상세 조회는 두 가지 작업이 고려되어야 한다.
        1. 해당 게시글 조회수 1 증가
        2. 게시글 데이터를 detail.html 뷰에 출력
         */
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findByID(id);
        model.addAttribute("board", boardDTO);
        return "detail";

    }

    //  수정할 게시글 정보 조회
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        // 게시글 수정하기 위해 id 통해서 해당 게시글의 정보 가져오기
        BoardDTO boardDTO = boardService.findByID(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "update";
    }

    // 게시글 수정
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "detail";
//        return "redirect:/board/" + board.getId(); <- 수정해도 조회수가 증가할 우려가 있어서 사용하지 않았다.

    }
}
