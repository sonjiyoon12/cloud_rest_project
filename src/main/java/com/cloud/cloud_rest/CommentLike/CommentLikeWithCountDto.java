package com.cloud.cloud_rest.CommentLike;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentLikeWithCountDto {
    private CommentLikeResponseDto commentLike;
    private Long likeCount;

}