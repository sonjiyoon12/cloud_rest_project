package com.cloud.cloud_rest.CommentLike;

import lombok.Getter;

@Getter
public class CommentLikeResponseDto {
    private final int likeCount;
    private final boolean isLiked;

    public CommentLikeResponseDto(int likeCount, boolean isLiked) {
        this.likeCount = likeCount;
        this.isLiked = isLiked;
    }
}