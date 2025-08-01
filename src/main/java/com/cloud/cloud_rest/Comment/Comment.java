package com.cloud.cloud_rest.Comment;


import com.cloud.cloud_rest.board.Board;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "board_comment_tb")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId; // 댓글 고유 ID (PK)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Board board; // 게시글 엔티티 (FK)

    @Column(name = "user_id", nullable = false)
    private Long userId; // 댓글 작성자 ID (FK)

    @Lob
    @Column(name = "content")
    private String content; // 댓글 내용

    @Column(name = "commented_at", columnDefinition = "datetime(6) default current_timestamp")
    private LocalDateTime commentedAt; // 댓글 작성일시

    @Column(name = "is_secret", nullable = false, columnDefinition = "boolean default false")
    private Boolean isSecret; // 비밀 댓글 여부


    @Builder
    public Comment(Board board, Long userId, String content, Boolean isSecret) {
        this.board = board;
        this.userId = userId;
        this.content = content;
        this.isSecret = isSecret;
        // commentedAt 는 DB에 자동 생성됨
    }
}
