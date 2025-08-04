package com.cloud.cloud_rest.Comment;

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
    private Long boardId;   // 댓글 생성 시 필요한 게시글 Id

    private String content; // 댓글 내용 (생성/수정 시 필수)
    private Long userId;    // 댓글 작성자 Id (권한 확인용으로 필수)
    private Boolean isSecret; // 비밀 댓글 여부 (선택 사항)
}
