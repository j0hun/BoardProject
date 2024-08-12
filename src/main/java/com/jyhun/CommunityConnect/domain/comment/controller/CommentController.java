package com.jyhun.CommunityConnect.domain.comment.controller;

import com.jyhun.CommunityConnect.domain.comment.dto.CommentRequestDTO;
import com.jyhun.CommunityConnect.domain.comment.dto.CommentResponseDTO;
import com.jyhun.CommunityConnect.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/boards/{boardId}/comments")
    public ResponseEntity<CommentResponseDTO> postComment(@PathVariable Long boardId, @RequestBody CommentRequestDTO commentRequestDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        CommentResponseDTO commentResponseDTO = commentService.addComment(commentRequestDTO, boardId, email);
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/api/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> patchComment(@PathVariable Long commentId, @RequestBody CommentRequestDTO commentRequestDTO) {
        CommentResponseDTO commentResponseDTO = commentService.modifyComment(commentRequestDTO, commentId);
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/api/comments/{commentId}")
    public ResponseEntity<CommentResponseDTO> deleteComment(@PathVariable Long commentId) {
        CommentResponseDTO commentResponseDTO = commentService.removeComment(commentId);
        return new ResponseEntity<>(commentResponseDTO, HttpStatus.NO_CONTENT);
    }

}
