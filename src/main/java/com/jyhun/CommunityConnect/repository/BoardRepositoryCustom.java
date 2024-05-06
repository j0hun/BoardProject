package com.jyhun.CommunityConnect.repository;

import com.jyhun.CommunityConnect.dto.BoardSearchDTO;
import com.jyhun.CommunityConnect.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<Board> findBoardPage(BoardSearchDTO boardSearchDTO, Pageable pageable);
}
