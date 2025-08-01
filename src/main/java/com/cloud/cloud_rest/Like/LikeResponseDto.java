package com.cloud.cloud_rest.Like;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikeResponseDto {

    private Long likeId; // 좋아요 고유 Id
    private Long boardId; // 좋아요가 눌린 게시글 Id
    private Long userId; // 좋아요를 누른 사용자 Id
    private LocalDateTime likeAt; // 좋아요를 누른 일시

    public LikeResponseDto(Like like) {
        this.likeId = like.getLikeId();
        this.boardId = like.getBoard().getBoardId();
        this.userId = like.getUserId();
        this.likeAt = like.getLikedAt();
    }
}
