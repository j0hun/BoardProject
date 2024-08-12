package com.jyhun.CommunityConnect.domain.comment.service;

import com.jyhun.CommunityConnect.domain.comment.dto.CommentRequestDTO;
import com.jyhun.CommunityConnect.domain.comment.dto.CommentResponseDTO;
import com.jyhun.CommunityConnect.domain.comment.entity.Comment;
import com.jyhun.CommunityConnect.domain.member.entity.Member;
import com.jyhun.CommunityConnect.domain.board.entity.Board;
import com.jyhun.CommunityConnect.domain.board.repository.BoardRepository;
import com.jyhun.CommunityConnect.domain.comment.repository.CommentRepository;
import com.jyhun.CommunityConnect.domain.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public CommentResponseDTO addComment(CommentRequestDTO commentRequestDTO, Long boardId, String email) {
        Comment comment = commentRequestDTO.toEntity();
        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email).orElse(null);
        comment.changeBoard(board);
        comment.changeMember(member);
        Comment savedComment = commentRepository.save(comment);
        CommentResponseDTO commentResponseDTO = CommentResponseDTO.toDTO(savedComment);
        return commentResponseDTO;
    }

    public CommentResponseDTO modifyComment(CommentRequestDTO commentRequestDTO, Long commentId) {
        Comment comment = commentRequestDTO.toEntity();
        Comment foundComment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
        foundComment.updateComment(comment);
        CommentResponseDTO commentResponseDTO = CommentResponseDTO.toDTO(foundComment);
        return commentResponseDTO;
    }

    public CommentResponseDTO removeComment(Long commentId){
        Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);
        commentRepository.delete(comment);
        CommentResponseDTO commentResponseDTO = CommentResponseDTO.toDTO(comment);
        return commentResponseDTO;
    }

}
