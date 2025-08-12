package com.cloud.cloud_rest.corporatePostLike;

import lombok.Getter;

@Getter
public class CorporatePostLikeResponseDto {
    private final int likeCount;
    private final boolean isLiked;

    public CorporatePostLikeResponseDto(int likeCount, boolean isLiked) {
        this.likeCount = likeCount;
        this.isLiked = isLiked;
    }
}