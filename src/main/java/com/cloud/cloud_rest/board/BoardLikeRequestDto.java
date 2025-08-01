package com.cloud.cloud_rest.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardLikeRequestDto {
    private Long boardId; // 좋아요 누를 게시글 Id
    private Long userId; // 좋아요 누른 사용자 Id
}
