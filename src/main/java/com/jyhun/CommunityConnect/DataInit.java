package com.jyhun.CommunityConnect;

import com.jyhun.CommunityConnect.domain.member.constant.Role;
import com.jyhun.CommunityConnect.domain.board.entity.Board;
import com.jyhun.CommunityConnect.domain.comment.entity.Comment;
import com.jyhun.CommunityConnect.domain.member.entity.Member;
import com.jyhun.CommunityConnect.domain.board.repository.BoardRepository;
import com.jyhun.CommunityConnect.domain.comment.repository.CommentRepository;
import com.jyhun.CommunityConnect.domain.member.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final BoardRepository boardRepository;
        private final MemberRepository memberRepository;
        private final CommentRepository commentRepository;
        private final PasswordEncoder passwordEncoder;

        public void dbInit() {
            Member member = new Member("admin", "admin@admin.com", passwordEncoder.encode("admin@admin.com"), Role.ADMIN);
            memberRepository.save(member);
            Board board = new Board("title", "content");
            board.changeMember(member);
            boardRepository.save(board);
            Comment comment = new Comment("comment");
            comment.changeBoard(board);
            comment.changeMember(member);
            commentRepository.save(comment);
        }
    }

}
