package com.cloud.cloud_rest.CommentLike;

import com.cloud.cloud_rest.Comment.Comment;
import com.cloud.cloud_rest.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserAndComment(User user, Comment comment);
}