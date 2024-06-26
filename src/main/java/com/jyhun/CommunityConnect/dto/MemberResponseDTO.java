package com.jyhun.CommunityConnect.dto;

import com.jyhun.CommunityConnect.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDTO {

    private Long id;

    private String name;

    private String email;

    public static MemberResponseDTO toDTO(Member member){
        return new MemberResponseDTO(member.getId(),member.getName(),member.getEmail());
    }

}
