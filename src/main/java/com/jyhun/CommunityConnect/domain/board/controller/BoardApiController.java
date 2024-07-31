package com.jyhun.CommunityConnect.domain.board.controller;

import com.jyhun.CommunityConnect.domain.board.dto.BoardRequestDTO;
import com.jyhun.CommunityConnect.domain.board.service.BoardService;
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

    @GetMapping("/api/boards/{id}")
    public ResponseEntity<Void> getBoard(@PathVariable Long id){
        boardService.findBoardById(id);
        boardService.view(id);
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
