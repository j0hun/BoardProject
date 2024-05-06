package com.jyhun.CommunityConnect.controller;

import com.jyhun.CommunityConnect.dto.BoardRequestDTO;
import com.jyhun.CommunityConnect.dto.BoardResponseDTO;
import com.jyhun.CommunityConnect.dto.BoardSearchDTO;
import com.jyhun.CommunityConnect.dto.CommentResponseDTO;
import com.jyhun.CommunityConnect.service.BoardService;
import com.jyhun.CommunityConnect.service.CommentService;
import com.jyhun.CommunityConnect.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final CommentService commentService;

    @GetMapping
    public String getBoards(BoardSearchDTO boardSearchDTO, Pageable pageable, Model model){
        Page<BoardResponseDTO> boards = boardService.findBoardPage(boardSearchDTO, pageable);
        model.addAttribute("boards",boards);
        model.addAttribute("boardSearchDTO",boardSearchDTO);
        model.addAttribute("maxPage",5);
        return "board/boards";
    }

    @GetMapping("/{boardId}")
    public String getBoard(@PathVariable Long boardId, Model model) {
        BoardResponseDTO board = boardService.findBoardById(boardId);
        boardService.viewCountUp(boardId);
        List<CommentResponseDTO> comments = commentService.findCommentsByBoardId(boardId);
        model.addAttribute("comments",comments);
        model.addAttribute("board",board);
        return "board/board";
    }

    @GetMapping("/createBoard")
    public String createBoardForm(){
        return "board/createBoard";
    }

    @PostMapping("/createBoard")
    public String createBoard(BoardRequestDTO boardRequestDTO, Model model, Principal principal){
        String email = principal.getName();
        BoardResponseDTO board = boardService.addBoard(boardRequestDTO,email);
        model.addAttribute("board",board);
        return "redirect:/boards";
    }

    @GetMapping("/{boardId}/editBoard")
    public String editBoardForm(@PathVariable Long boardId, Model model){
        BoardResponseDTO board = boardService.findBoardById(boardId);
        model.addAttribute("board",board);
        return "board/editBoard";
    }

    @PostMapping("/{boardId}/editBoard")
    public String editBoard(@PathVariable Long boardId, BoardRequestDTO boardRequestDTO, Model model) {
        BoardResponseDTO board = boardService.modifyBoard(boardRequestDTO, boardId);
        model.addAttribute("board",board);
        return "redirect:/boards/{boardId}";
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId) {
        boardService.removeBoard(boardId);
        return ResponseEntity.noContent().build();
    }

}
