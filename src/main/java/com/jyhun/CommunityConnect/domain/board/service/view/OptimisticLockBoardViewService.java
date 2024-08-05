package com.jyhun.CommunityConnect.domain.board.service.view;

import com.jyhun.CommunityConnect.domain.board.entity.Board;
import com.jyhun.CommunityConnect.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Primary
public class OptimisticLockBoardViewService implements BoardViewService {

    private final BoardRepository boardRepository;

    @Transactional
    @Override
    public void view(Long id) {
        try {
            Board board = boardRepository.findById(id).orElseThrow();
            board.updateView(1);
            boardRepository.saveAndFlush(board);
        } catch (ObjectOptimisticLockingFailureException e) {
            e.printStackTrace();
        }
    }
}
