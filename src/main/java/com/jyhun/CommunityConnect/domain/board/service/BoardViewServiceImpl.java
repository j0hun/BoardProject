package com.jyhun.CommunityConnect.domain.board.service;

import com.jyhun.CommunityConnect.domain.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class BoardViewServiceImpl implements BoardViewService {

    private final BoardRepository boardRepository;

    @Override
    public void view(Long id) {
        boardRepository.updateViewCount(id);
    }
}
