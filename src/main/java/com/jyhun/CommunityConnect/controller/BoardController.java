package com.jyhun.CommunityConnect.controller;

import com.jyhun.CommunityConnect.dto.BoardRequestDTO;
import com.jyhun.CommunityConnect.dto.BoardResponseDTO;
import com.jyhun.CommunityConnect.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public String getBoards(Model model){
        List<BoardResponseDTO> boards = boardService.findBoards();
        model.addAttribute("boards",boards);
        return "board/boards";
    }

    @GetMapping("/boards/{boardId}")
    public String getBoard(@PathVariable Long boardId, Model model) {
        BoardResponseDTO board = boardService.findBoardById(boardId);
        model.addAttribute("board",board);
        return "board/board";
    }

    @GetMapping("/boards/createBoard")
    public String createBoardForm(){
        return "board/createBoard";
    }

    @PostMapping("/boards/createBoard")
    public String createBoard(BoardRequestDTO boardRequestDTO, Model model, Principal principal){
        String name = principal.getName();
        BoardResponseDTO board = boardService.addBoard(boardRequestDTO,name);
        model.addAttribute("board",board);
        return "redirect:/boards";
    }

    @GetMapping("/boards/{boardId}/editBoard")
    public String editBoardForm(@PathVariable Long boardId, Model model){
        BoardResponseDTO board = boardService.findBoardById(boardId);
        model.addAttribute("board",board);
        return "board/editBoard";
    }

    @PostMapping("/boards/{boardId}/editBoard")
    public String editBoard(@PathVariable Long boardId, BoardRequestDTO boardRequestDTO, Model model) {
        BoardResponseDTO board = boardService.modifyBoard(boardRequestDTO, boardId);
        model.addAttribute("board",board);
        return "redirect:/boards/{boardId}";
    }

    @GetMapping("/boards/{boardId}/deleteBoard")
    public String deleteBoard(@PathVariable Long boardId) {
        boardService.removeBoard(boardId);
        return "redirect:/boards";
    }

}
