package com.cloud.cloud_rest.Comment;

import com.cloud.cloud_rest.board.Board;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board_comment_tb")
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @ToString.Exclude
    @JsonBackReference
    private Board board;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "commented_at")
    @CreationTimestamp
    private LocalDateTime commentedAt;

    @Column(name = "is_secret")
    private Boolean isSecret;

    @Column(name = "like_count", nullable = false, columnDefinition = "integer default 0")
    private int likeCount;

    // 댓글 내용과 비밀 댓글 여부를 업데이트하는 메서드
    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.isSecret = requestDto.getIsSecret();
    }


}
