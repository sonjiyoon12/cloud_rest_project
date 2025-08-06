package com.cloud.cloud_rest.Like;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikeRequestDto {

    // 좋아요를 누른 사용자 ID (필수)
    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    // 좋아요를 누를 게시글 ID (게시글 또는 댓글 중 하나만 필수)
    private Long boardId;

    // 좋아요를 누를 댓글 ID (게시글 또는 댓글 중 하나만 필수)
    private Long commentId;
}