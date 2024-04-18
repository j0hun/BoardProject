package com.jyhun.CommunityConnect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    public void setBoard(Board board) {
        this.board = board;
        if(board != null) {
            board.getCommentList().add(this);
        }
    }

    public void setMember(Member member){
        this.member = member;
        if(member != null) {
            member.getCommentList().add(this);
        }
    }

    public void updateComment(Comment comment) {
        this.content = comment.getContent();
    }

}
