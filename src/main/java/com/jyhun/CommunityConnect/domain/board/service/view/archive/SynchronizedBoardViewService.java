package com.jyhun.CommunityConnect.domain.board.service.view.archive;

import com.jyhun.CommunityConnect.domain.board.entity.Board;
import com.jyhun.CommunityConnect.domain.board.repository.BoardRepository;
import com.jyhun.CommunityConnect.domain.board.service.view.BoardViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SynchronizedBoardViewService implements BoardViewService {

    private final BoardRepository boardRepository;

    @Override
    public synchronized void view(Long id) {
        Board board = boardRepository.findById(id).orElseThrow();
        board.increaseView(1L);
        boardRepository.saveAndFlush(board);
    }
}
