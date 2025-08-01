package com.cloud.cloud_rest.board;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardCommentRequestDto {
    private String content; // 댓글 내용
    private Long userId; // 댓글 작성자 Id
    private Boolean isSecret; // 비밀 댓글

    // 댓글 엔티티 생성
    public BoardComment toEntity(Board board) {
        return BoardComment.builder()
                .board(board)
                .userId(userId)
                .content(content)
                .isSecret(isSecret)
                .build();
    }
}
