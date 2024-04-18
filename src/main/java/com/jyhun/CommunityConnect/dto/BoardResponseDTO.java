package com.jyhun.CommunityConnect.dto;

import com.jyhun.CommunityConnect.entity.Board;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardResponseDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String memberName;

    public static BoardResponseDTO toDTO(Board board){
        BoardResponseDTO boardResponseDTO = new BoardResponseDTO();
        boardResponseDTO.setId(board.getId());
        boardResponseDTO.setTitle(board.getTitle());
        boardResponseDTO.setContent(board.getContent());
        boardResponseDTO.setCreatedAt(board.getCreatedAt());
        boardResponseDTO.setUpdatedAt(board.getUpdatedAt());
        boardResponseDTO.setMemberName(board.getMember().getName());
        return boardResponseDTO;
    }

}
