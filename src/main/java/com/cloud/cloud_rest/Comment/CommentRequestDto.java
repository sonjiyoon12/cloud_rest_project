package com.cloud.cloud_rest.Comment;

import com.cloud.cloud_rest.board.Board;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {

    // 댓글 수정 및 삭제 시 필요한 댓글 고유 ID
    private Long commentId;

    // 댓글 생성 시 필요한 게시글 ID
    @NotNull(message = "게시글 ID는 필수입니다.")
    private Long boardId;

    // 댓글 내용 (생성/수정 시 필수)
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

    // 댓글 작성자 ID (권한 확인용으로 필수)
    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    // 비밀 댓글 여부 (선택 사항)
    private Boolean isSecret;

    public Comment toEntity(Board board) {
        return Comment.builder()
                .board(board)
                .userId(userId)
                .content(content)
                .isSecret(isSecret != null ? isSecret : false)
                .build();
    }
}
