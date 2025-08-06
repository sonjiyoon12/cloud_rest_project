package com.cloud.cloud_rest.CommentLike;

import com.cloud.cloud_rest.Comment.Comment;
import com.cloud.cloud_rest.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 이 리포지토리는 CommentLike 엔티티만 다룹니다.
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    // 댓글과 사용자로 좋아요를 찾습니다.
    Optional<CommentLike> findByCommentAndUser(Comment comment, User user);
}
