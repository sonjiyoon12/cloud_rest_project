package com.cloud.cloud_rest.CommentLike;

import com.cloud.cloud_rest.Comment.Comment;
import com.cloud.cloud_rest.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    @Query("select cl from CommentLike cl where cl.user = :user and cl.comment = :comment")
    Optional<CommentLike> findByUserAndComment(@Param("user") User user, @Param("comment") Comment comment);

}