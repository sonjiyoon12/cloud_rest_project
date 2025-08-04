package com.cloud.cloud_rest.Comment;

import com.cloud.cloud_rest.board.Board;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringExclude;
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
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @ToStringExclude
    private Board board;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "commented_at", columnDefinition = "datetime(6) default current_timestamp")
    private LocalDateTime commentedAt;

    @Column(name = "is_secret", nullable = false, columnDefinition = "boolean default false")
    private Boolean isSecret;


    @Builder
    public Comment(Board board, Long userId, String content, Boolean isSecret) {
        this.board = board;
        this.userId = userId;
        this.content = content;
        this.isSecret = isSecret;
    }

    // 댓글 내용과 비밀 댓글 여부를 업데이트하는 메서드 추가
    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.isSecret = requestDto.getIsSecret();
    }
}
