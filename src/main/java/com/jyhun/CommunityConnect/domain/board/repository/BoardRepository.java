package com.jyhun.CommunityConnect.domain.board.repository;

import com.jyhun.CommunityConnect.domain.board.entity.Board;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board,Long>, BoardRepositoryCustom {
    @Modifying
    @Query("UPDATE Board b SET b.viewCount = b.viewCount + 1 WHERE b.id = :boardId")
    int updateViewCount(@Param("boardId") Long boardId);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM Board b where b.id = :id")
    Board findByWithPessimisticLock(Long id);



}
