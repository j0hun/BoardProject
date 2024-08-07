package com.jyhun.CommunityConnect.domain.board.service.view;

import com.jyhun.CommunityConnect.domain.board.entity.Board;
import com.jyhun.CommunityConnect.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PessimisticLockBoardViewService implements BoardViewService{

    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public void view(Long id) {
        Board board = boardRepository.findByWithPessimisticLock(id);
        board.increaseView(1L);
        boardRepository.saveAndFlush(board);
    }
}
