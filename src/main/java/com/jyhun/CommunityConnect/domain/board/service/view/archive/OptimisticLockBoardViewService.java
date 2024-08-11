package com.jyhun.CommunityConnect.domain.board.service.view.archive;

import com.jyhun.CommunityConnect.domain.board.entity.Board;
import com.jyhun.CommunityConnect.domain.board.repository.BoardRepository;
import com.jyhun.CommunityConnect.domain.board.service.view.BoardViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticLockBoardViewService implements BoardViewService {

    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public void view(Long id) {
        Board board = boardRepository.findById(id).orElseThrow();
        board.increaseView(1L);
        boardRepository.saveAndFlush(board);
    }

    public void updateViewWithRetry(Long id) throws InterruptedException {
        while (true) {
            try {
                view(id);
                break;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }

}
