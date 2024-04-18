package com.jyhun.CommunityConnect.dto;

import com.jyhun.CommunityConnect.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentResponseDTO {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long boardId;
    private String memberName;

    public static CommentResponseDTO toDTO(Comment comment) {
        CommentResponseDTO commentResponseDTO = new CommentResponseDTO();
        commentResponseDTO.setId(comment.getId());
        commentResponseDTO.setContent(comment.getContent());
        commentResponseDTO.setCreatedAt(comment.getCreatedAt());
        commentResponseDTO.setUpdatedAt(comment.getUpdatedAt());
        commentResponseDTO.setBoardId(comment.getBoard().getId());
        commentResponseDTO.setMemberName(comment.getMember().getName());
        return commentResponseDTO;
    }

}
