package com.jyhun.CommunityConnect.dto;

import com.jyhun.CommunityConnect.entity.Board;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardRequestDTO {

    private String title;
    private String content;

    public Board toEntity(){
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }

}
