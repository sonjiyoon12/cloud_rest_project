package com.cloud.cloud_rest.CommentLike;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentLikeResponseDto {
    private Long id;
    private Long commentId;
    private Long userId;
    private LocalDateTime likeAt;

    @Builder
    public CommentLikeResponseDto(CommentLike commentLike) {
        this.id = commentLike.getId();
        this.commentId = commentLike.getComment().getCommentId();
        this.userId = commentLike.getUser().getUserId();
        this.likeAt = commentLike.getLikedAt();
    }
}
