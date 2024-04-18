package com.jyhun.CommunityConnect.service;

import com.jyhun.CommunityConnect.dto.CommentRequestDTO;
import com.jyhun.CommunityConnect.dto.CommentResponseDTO;
import com.jyhun.CommunityConnect.entity.Board;
import com.jyhun.CommunityConnect.entity.Comment;
import com.jyhun.CommunityConnect.entity.Member;
import com.jyhun.CommunityConnect.repository.BoardRepository;
import com.jyhun.CommunityConnect.repository.CommentRepository;
import com.jyhun.CommunityConnect.repository.MemberRepository;
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

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> findCommentsByBoardId(Long boardId){
        List<Comment> commentList = commentRepository.findAllByBoardId(boardId);
        List<CommentResponseDTO> commentResponseDTOList = new ArrayList<>();
        for (Comment comment : commentList) {
            CommentResponseDTO commentResponseDTO = CommentResponseDTO.toDTO(comment);
            commentResponseDTOList.add(commentResponseDTO);
        }
        return commentResponseDTOList;
    }

    public CommentResponseDTO addComment(CommentRequestDTO commentRequestDTO, Long boardId, String email) {
        Comment comment = commentRequestDTO.toEntity();
        Board board = boardRepository.findById(boardId).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByEmail(email);
        comment.setBoard(board);
        comment.setMember(member);
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
