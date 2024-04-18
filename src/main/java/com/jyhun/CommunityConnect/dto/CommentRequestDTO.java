package com.jyhun.CommunityConnect.dto;

import com.jyhun.CommunityConnect.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDTO {

    private String content;

    public Comment toEntity(){
        return Comment.builder()
                .content(this.content)
                .build();
    }

}
