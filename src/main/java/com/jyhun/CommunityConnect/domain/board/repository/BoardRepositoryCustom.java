package com.jyhun.CommunityConnect.domain.board.repository;

import com.jyhun.CommunityConnect.domain.board.dto.BoardSearchDTO;
import com.jyhun.CommunityConnect.domain.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<Board> findBoardPage(BoardSearchDTO boardSearchDTO, Pageable pageable);
}
