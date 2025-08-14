package com.cloud.cloud_rest.Comment;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {

    private Long commentId; // 댓글 고유 Id
    private String content; // 댓글 내용
    private Long userId; // 댓글 작성자 Id
    private Boolean isSecret; // 비밀 댓글 여부
    private LocalDateTime commentedAt; // 댓글 작성일시
    private String authorName; // 댓글 작성자 이름


    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.userId = comment.getUser().getUserId();
        this.isSecret = comment.getIsSecret();
        this.commentedAt = comment.getCreatedAt();
        this.authorName = comment.getUser().getUsername();
    }
}
