package com.cloud.cloud_rest.Like;

import com.cloud.cloud_rest.board.Board;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LikeRequestDto {

    // 좋아요를 누를 게시글 ID
    @NotNull(message = "게시글 ID는 필수입니다.")
    private Long boardId;

    // 좋아요를 누른 사용자 ID
    private Long userId;


    public Like toEntity(Board board) {
        return Like.builder()
                .board(board)
                .userId(userId)
                .build();
    }
}
