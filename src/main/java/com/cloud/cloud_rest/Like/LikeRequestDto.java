package com.cloud.cloud_rest.Like;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
class LikeRequestDto {
    private Long boardId; // 좋아요 누를 게시글 Id
    private Long userId; // 좋아요 누를 사용자Id
}

