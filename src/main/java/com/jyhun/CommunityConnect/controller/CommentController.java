package com.jyhun.CommunityConnect.controller;

import com.jyhun.CommunityConnect.dto.CommentRequestDTO;
import com.jyhun.CommunityConnect.dto.CommentResponseDTO;
import com.jyhun.CommunityConnect.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/boards/{boardId}")
    public ResponseEntity<CommentResponseDTO> postComment(@PathVariable Long boardId, @RequestBody CommentRequestDTO commentRequestDTO, Principal principal) {
        String email = principal.getName();
        CommentResponseDTO commentResponseDTO = commentService.addComment(commentRequestDTO, boardId, email);
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> patchComment(@PathVariable Long commentId, @RequestBody CommentRequestDTO commentRequestDTO) {
        CommentResponseDTO commentResponseDTO = commentService.modifyComment(commentRequestDTO, commentId);
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> deleteComment(@PathVariable Long commentId) {
        CommentResponseDTO commentResponseDTO = commentService.removeComment(commentId);
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.NO_CONTENT);
    }

}
