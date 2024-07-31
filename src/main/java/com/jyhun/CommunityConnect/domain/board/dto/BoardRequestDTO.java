package com.jyhun.CommunityConnect.domain.board.dto;

import com.jyhun.CommunityConnect.domain.board.entity.Board;
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
