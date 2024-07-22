package com.jyhun.CommunityConnect.repository;

import com.jyhun.CommunityConnect.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board,Long>, BoardRepositoryCustom {
    @Modifying
    @Query("UPDATE Board b SET b.viewCount = b.viewCount + 1 WHERE b.id = :boardId")
    int updateViewCount(@Param("boardId") Long boardId);
}
