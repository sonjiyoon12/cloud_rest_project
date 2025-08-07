package com.cloud.cloud_rest.CommentLike;

import lombok.Data;

public class CommentLikeRequestDto {

    @Data
    public static class SaveDto {
        private Long commentId;
        private Long userId;
    }

}
