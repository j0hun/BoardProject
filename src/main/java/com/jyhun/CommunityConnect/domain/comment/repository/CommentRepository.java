package com.jyhun.CommunityConnect.domain.comment.repository;

import com.jyhun.CommunityConnect.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAllByBoardId(Long boardId);

}
