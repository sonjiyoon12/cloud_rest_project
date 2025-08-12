package com.cloud.cloud_rest.CommentLike;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest.Comment.Comment;
import com.cloud.cloud_rest.Comment.CommentRepository;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentLikeService {

    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;

    public CommentLikeResponseDto toggleCommentLike(Long commentId, SessionUser sessionUser) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 댓글을 찾을 수 없습니다: " + commentId));

        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다: " + sessionUser.getId()));

        return commentLikeRepository.findByUserAndComment(user, comment)
                .map(this::removeLike)
                .orElseGet(() -> addLike(user, comment));
    }

    private CommentLikeResponseDto addLike(User user, Comment comment) {
        commentLikeRepository.save(CommentLike.builder().user(user).comment(comment).build());
        comment.updateLikeCount(comment.getLikeCount() + 1);
        return new CommentLikeResponseDto(comment.getLikeCount(), true);
    }

    private CommentLikeResponseDto removeLike(CommentLike like) {
        Comment comment = like.getComment();
        commentLikeRepository.delete(like);
        comment.updateLikeCount(comment.getLikeCount() - 1);
        return new CommentLikeResponseDto(comment.getLikeCount(), false);
    }
}