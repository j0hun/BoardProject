package com.jyhun.CommunityConnect.dto;

import com.jyhun.CommunityConnect.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class MemberResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentResponseDTO> commentResponseDTOList;
    private List<BoardResponseDTO> boardResponseDTOList;

    public static MemberResponseDTO toDTO(Member member){
        MemberResponseDTO memberResponseDTO = new MemberResponseDTO();
        memberResponseDTO.setId(member.getId());
        memberResponseDTO.setName(member.getName());
        memberResponseDTO.setPassword(member.getPassword());
        memberResponseDTO.setAddress(member.getAddress());
        memberResponseDTO.setCreatedAt(member.getCreatedAt());
        memberResponseDTO.setUpdatedAt(member.getUpdatedAt());
        if (member.getCommentList() != null) {
            memberResponseDTO.setCommentResponseDTOList(
                    member.getCommentList().stream()
                            .map(CommentResponseDTO::toDTO)
                            .collect(Collectors.toList())
            );
        }

        if (member.getBoardList() != null) {
            memberResponseDTO.setBoardResponseDTOList(
                    member.getBoardList().stream()
                            .map(BoardResponseDTO::toDTO)
                            .collect(Collectors.toList())
            );
        }

        return memberResponseDTO;
    }

}
