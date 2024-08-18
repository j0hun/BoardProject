package com.jyhun.CommunityConnect.domain.board.dto;

import com.jyhun.CommunityConnect.domain.comment.dto.CommentResponseDTO;
import com.jyhun.CommunityConnect.domain.board.entity.Board;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class BoardResponseDTO {

    private Long id;
    private String title;
    private String content;
    private Long viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String author;
    private List<CommentResponseDTO> commentResponseDTOList;

    public static BoardResponseDTO toDTO(Board board){
        BoardResponseDTO boardResponseDTO = new BoardResponseDTO();
        boardResponseDTO.setId(board.getId());
        boardResponseDTO.setTitle(board.getTitle());
        boardResponseDTO.setContent(board.getContent());
        boardResponseDTO.setViewCount(board.getViewCount());
        boardResponseDTO.setCreatedAt(board.getCreatedAt());
        boardResponseDTO.setUpdatedAt(board.getUpdatedAt());
        boardResponseDTO.setAuthor(board.getMember().getName());
        if (board.getCommentList() != null) {
            boardResponseDTO.setCommentResponseDTOList(
                    board.getCommentList().stream()
                            .map(CommentResponseDTO::toDTO)
                            .collect(Collectors.toList())
            );
        }
        return boardResponseDTO;
    }

}
