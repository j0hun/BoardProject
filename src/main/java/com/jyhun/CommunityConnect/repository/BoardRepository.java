package com.jyhun.CommunityConnect.repository;

import com.jyhun.CommunityConnect.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}
