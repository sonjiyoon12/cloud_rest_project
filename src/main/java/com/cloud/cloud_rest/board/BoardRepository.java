package com.cloud.cloud_rest.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {

    // 키워드를 포함하는 제목 또는 내용의 게시글을 페이징하여 조회
    @Query("SELECT b FROM Board b WHERE b.title LIKE %:keyword% OR b.content LIKE %:keyword%")
    Page<Board> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);

    // 특정 사용자가 댓글을 작성한 모든 게시글을 조회
    @Query("SELECT b FROM Board b WHERE b.boardId IN (SELECT c.board.boardId FROM Comment c WHERE c.userId = :userId)")
    Page<Board> findBoardsCommentedByUser(@Param("userId") Long userId, Pageable pageable);
}
