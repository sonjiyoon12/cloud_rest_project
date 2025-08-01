package com.cloud.cloud_rest.Comment;


import com.cloud.cloud_rest.board.Board;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {

    private Long commentId; // 댓글 수정/삭제 시 필요한 댓글 고유 Id

    @NotBlank(message = "댓글 내용은 필수 입니다")
    private  String content; // 댓글 내용( 생성/수정 시 필수)

    @NotBlank(message = "사용자 Id는 필수 입니다")
    private Long userId; // 댓글 작성자 Id (권한 확인용으로 필수)

    private Boolean isSecret; // 비밀 댓글 여부 (선택 사항) --> 기본적으로 false 처리 됨




    // BoardComment 엔티티 생성하는 메서드
    // 댓글 엔티티 생성
    public Comment toEntity(Board board) {
        return Comment.builder()
                .board(board)
                .userId(userId)
                .content(content)
                // isSecret 값이 null 이면 기본적으로 false
                .isSecret(isSecret != null ? this.isSecret : false)
                .build();
    }
}
