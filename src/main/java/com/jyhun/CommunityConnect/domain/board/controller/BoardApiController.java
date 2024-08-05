package com.jyhun.CommunityConnect.domain.board.controller;

import com.jyhun.CommunityConnect.domain.board.dto.BoardRequestDTO;
import com.jyhun.CommunityConnect.domain.board.service.BoardService;
import com.jyhun.CommunityConnect.domain.board.service.view.BoardViewService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/api/boards/{id}")
    public ResponseEntity<Void> getBoard(@PathVariable Long id){
        boardService.findBoardById(id);
        boardViewService.view(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/api/boards")
    public ResponseEntity<Void> postBoard(@RequestBody BoardRequestDTO boardRequestDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        boardService.addBoard(boardRequestDTO, email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
