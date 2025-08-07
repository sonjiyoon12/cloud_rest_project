package com.cloud.cloud_rest.CommentLike;

import com.cloud.cloud_rest.Comment.Comment;
import com.cloud.cloud_rest.Comment.CommentRepository;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    /**
     * 댓글 좋아요를 등록하거나 취소하고, 변경된 좋아요 개수를 포함한 응답을 반환합니다.
     *
     * @return 좋아요 등록 시 CommentLikeWithCountDto, 취소 시 null을 반환합니다.
     */
    @Transactional
    public CommentLikeWithCountDto toggleCommentLike(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (comment.getIsSecret() && !comment.getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("비밀 댓글에는 작성자만 좋아요를 누를 수 있습니다.");
        }

        Optional<CommentLike> existingLike = commentLikeRepository.findByCommentAndUser(comment, user);

        CommentLike savedLike = null;
        if (existingLike.isPresent()) {
            // 이미 좋아요를 누른 경우, 좋아요 취소
            commentLikeRepository.delete(existingLike.get());
        } else {
            // 좋아요를 누르지 않은 경우, 좋아요 등록
            CommentLike newLike = CommentLike.builder()
                    .comment(comment)
                    .user(user)
                    .build();
            savedLike = commentLikeRepository.save(newLike);
        }

        // 좋아요 개수를 조회하고, 응답 DTO를 반환
        Long likeCount = commentLikeRepository.countByComment(comment);

        // 좋아요 취소 시에는 null을 반환
        if (savedLike == null) {
            return null;
        }
        CommentLikeResponseDto commentLikeResponseDto = savedLike != null ?
                new CommentLikeResponseDto(savedLike) : null;
        // 좋아요 등록 시에는 DTO를 반환
        return CommentLikeWithCountDto.builder()
                .commentLike(commentLikeResponseDto)
                .likeCount(likeCount)
                .build();
    }

    /**
     * 특정 댓글의 좋아요 개수를 조회합니다.
     *
     * @return 좋아요 개수
     */
    @Transactional(readOnly = true)
    public long getCommentLikeCount(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        return commentLikeRepository.countByComment(comment);
    }
}