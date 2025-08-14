package com.cloud.cloud_rest.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c WHERE c.board.boardId = :boardId")
    List<Comment> findAllByBoardId(@Param("boardId") Long boardId);
    List<Comment> findByUser_UserId(Long userId);
}