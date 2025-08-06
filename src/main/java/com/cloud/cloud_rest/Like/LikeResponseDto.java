package com.cloud.cloud_rest.Like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponseDto {
    private boolean isLiked; // 좋아요 상태
    private int newLikeCount; // 변경된 좋아요 수
}