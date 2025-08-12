package com.cloud.cloud_rest.corporatePostLike;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CorporatePostLikeResponseDto {

    /**
     * 좋아요/좋아요 취소 성공 응답을 위한 DTO
     */
    @Getter
    public static class ToggleLikeDto {
        private String message;

        @Builder
        public ToggleLikeDto(boolean liked) {
            this.message = liked ? "좋아요가 추가되었습니다." : "좋아요가 취소되었습니다.";
        }
    }

    /**
     * 게시물 좋아요 수를 조회하기 위한 DTO
     */
    @Getter
    public static class CountDto {
        private Long postId;
        private int likeCount;

        @Builder
        public CountDto(Long postId, int likeCount) {
            this.postId = postId;
            this.likeCount = likeCount;
        }
    }
}