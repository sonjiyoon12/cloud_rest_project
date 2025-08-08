package com.cloud.cloud_rest.corporateCommentLike;

import com.cloud.cloud_rest.corporateComment.CorporateCommentRepository;
import com.cloud.cloud_rest.corporateComment.CorporatePostComment;
import com.cloud.cloud_rest.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CorporateCommentLikeService {

    private final CorporateCommentRepository commentRepository;
    private final CorporateCommentLikeRepository commentLikeRepository;

    public CorporateCommentLikeResponseDto toggleCommentLike(Long commentId, User sessionUser) {
        CorporatePostComment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 ID의 댓글을 찾을 수 없습니다: " + commentId));

        Optional<CorporateCommentLike> existingLike = commentLikeRepository.findByUserAndComment(sessionUser, comment);

        if (existingLike.isPresent()) {
            commentLikeRepository.delete(existingLike.get());
            comment.updateLikeCount(comment.getLikeCount() - 1);
            return new CorporateCommentLikeResponseDto(comment.getLikeCount(), false);
        } else {
            CorporateCommentLike newLike = CorporateCommentLike.builder()
                    .user(sessionUser)
                    .comment(comment)
                    .build();
            commentLikeRepository.save(newLike);
            comment.updateLikeCount(comment.getLikeCount() + 1);
            return new CorporateCommentLikeResponseDto(comment.getLikeCount(), true);
        }
    }
}