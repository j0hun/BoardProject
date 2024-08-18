package com.jyhun.CommunityConnect.domain.board.controller;

import com.jyhun.CommunityConnect.domain.board.dto.BoardRequestDTO;
import com.jyhun.CommunityConnect.domain.board.dto.BoardResponseDTO;
import com.jyhun.CommunityConnect.domain.board.dto.BoardSearchDTO;
import com.jyhun.CommunityConnect.domain.board.service.BoardService;
import com.jyhun.CommunityConnect.domain.board.service.view.BoardViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class BoardApiController {

    private final BoardService boardService;
    private final BoardViewService boardViewService;

    @GetMapping("/api/boards/{boardId}")
    public ResponseEntity<BoardResponseDTO> getBoard(@PathVariable Long boardId){
        boardViewService.view(boardId);
        BoardResponseDTO board = boardService.findBoardById(boardId);
        return new ResponseEntity<>(board,HttpStatus.OK);
    }

    @GetMapping("/api/boards")
    public ResponseEntity<Page<BoardResponseDTO>> getBoardsPage(BoardSearchDTO boardSearchDTO,Pageable pageable) {
        Page<BoardResponseDTO> boardPage = boardService.findBoardPage(boardSearchDTO, pageable);
        return new ResponseEntity<>(boardPage,HttpStatus.OK);
    }

    @PostMapping("/api/categories/{categoryId}/boards")
    public ResponseEntity<Void> postBoard(@PathVariable Long categoryId, @RequestBody BoardRequestDTO boardRequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        boardService.addBoard(boardRequestDTO,categoryId, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/boards/{boardId}/likes")
    public ResponseEntity<Void> likeBoard(@PathVariable Long boardId){
        boardService.incrementLikeCount(boardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/api/boards/{boardId}")
    public ResponseEntity<Void> patchBoard(@PathVariable Long boardId, @RequestBody BoardRequestDTO boardRequestDTO) {
        boardService.modifyBoard(boardRequestDTO,boardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/boards/{boardId}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long boardId){
        boardService.removeBoard(boardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping("/api/board/popular")
//    public ResponseEntity<Void> getBoardPopular(Pageable pageable) {
//        boardService.findBoardsPopular(pageable);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
