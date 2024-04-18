package com.jyhun.CommunityConnect.dto;

import com.jyhun.CommunityConnect.entity.Board;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String memberName;
    private List<CommentResponseDTO> commentResponseDTOList;

    public static BoardResponseDTO toDTO(Board board){
        BoardResponseDTO boardResponseDTO = new BoardResponseDTO();
        boardResponseDTO.setId(board.getId());
        boardResponseDTO.setTitle(board.getTitle());
        boardResponseDTO.setContent(board.getContent());
        boardResponseDTO.setCreatedAt(board.getCreatedAt());
        boardResponseDTO.setUpdatedAt(board.getUpdatedAt());
        boardResponseDTO.setMemberName(board.getMember().getName());
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
