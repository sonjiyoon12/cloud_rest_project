package com.cloud.cloud_rest.CommentLike;

import com.cloud.cloud_rest.Comment.Comment;
import com.cloud.cloud_rest.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    // 댓글과 사용자로 좋아요를 찾습니다.
    Optional<CommentLike> findByCommentAndUser(Comment comment, User user);

    // 댓글 좋아요 개수를 세는 쿼리 메서드
    long countByComment(Comment comment);
}
