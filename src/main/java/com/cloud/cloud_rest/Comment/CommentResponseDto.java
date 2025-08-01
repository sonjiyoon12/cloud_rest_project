package com.cloud.cloud_rest.Comment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Long commentId; // 댓글 고유 Id
    private String content; // 댓글 내용
    private Long userId; // 댓글 작성자 Id
    private Boolean isSecret; // 비밀 댓글 여부
    private LocalDateTime commentedAt; // 댓글 작성일시

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.userId = comment.getUserId();
        this.isSecret = comment.getIsSecret();
        this.commentedAt = comment.getCommentedAt();
    }
}
