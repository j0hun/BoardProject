package com.jyhun.CommunityConnect.domain.board.entity;

import com.jyhun.CommunityConnect.domain.comment.entity.Comment;
import com.jyhun.CommunityConnect.domain.member.entity.Member;
import com.jyhun.CommunityConnect.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Long viewCount;
    
    /* OptimisticLock 경우에 사용
    @Version
    private Long version;
    */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public Board(String title, String content) {
        this.title = title;
        this.content = content;
        this.viewCount = 0L;
    }

    public void increaseView(Long count){
        this.viewCount += count;
    }

    public void changeMember(Member member) {
        this.member = member;
        if(member != null) {
            member.getBoardList().add(this);
        }
    }

    public void updateBoard(Board board){
        this.title = board.getTitle();
        this.content = board.getContent();
    }

}
