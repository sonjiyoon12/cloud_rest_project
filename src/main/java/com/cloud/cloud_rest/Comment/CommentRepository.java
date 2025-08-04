package com.cloud.cloud_rest.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글에 속한 모든 댓글을 페이징하여 조회하는 메서드
    Page<Comment> findByBoardId(Long boardId, Pageable pageable);

    // 댓글 ID와 사용자 ID로 댓글을 조회하여 소유권 확인에 사용하는 메서드
    Optional<Comment> findByIdAndUserId(Long commentId, Long userId);

    // 사용자의 모든 댓글을 삭제하는 메서드
    void deleteByUserId(Long userId);

}
