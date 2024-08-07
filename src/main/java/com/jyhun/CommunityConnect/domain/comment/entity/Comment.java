package com.jyhun.CommunityConnect.domain.comment.entity;

import com.jyhun.CommunityConnect.domain.board.entity.Board;
import com.jyhun.CommunityConnect.domain.member.entity.Member;
import com.jyhun.CommunityConnect.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public void changeBoard(Board board) {
        this.board = board;
        if(board != null) {
            board.getCommentList().add(this);
        }
    }

    public void changeMember(Member member){
        this.member = member;
        if(member != null) {
            member.getCommentList().add(this);
        }
    }

    public void updateComment(Comment comment) {
        this.content = comment.getContent();
    }

    @Builder
    public Comment(String content) {
        this.content = content;
    }
}
