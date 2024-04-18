package com.jyhun.CommunityConnect;

import com.jyhun.CommunityConnect.constant.Role;
import com.jyhun.CommunityConnect.entity.Board;
import com.jyhun.CommunityConnect.entity.Member;
import com.jyhun.CommunityConnect.repository.BoardRepository;
import com.jyhun.CommunityConnect.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        Member member = new Member("name","email","password","address", Role.ADMIN);
        memberRepository.save(member);
        Board board = new Board("title","content");
        board.setMember(member);
        boardRepository.save(board);
    }

}
