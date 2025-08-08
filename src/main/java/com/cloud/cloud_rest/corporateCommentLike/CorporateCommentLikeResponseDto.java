package com.cloud.cloud_rest.corporateCommentLike;

import lombok.Getter;

@Getter
public class CorporateCommentLikeResponseDto {
    private final int likeCount;
    private final boolean isLiked;

    public CorporateCommentLikeResponseDto(int likeCount, boolean isLiked) {
        this.likeCount = likeCount;
        this.isLiked = isLiked;
    }
}