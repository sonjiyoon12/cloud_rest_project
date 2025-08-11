package com.cloud.cloud_rest.Comment;

import com.cloud.cloud_rest._global._core.common.Timestamped;
import com.cloud.cloud_rest.board.Board;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "board_comment_tb")
@Builder
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    @ToString.Exclude
    @JsonBackReference
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY) // 1. User 객체로 직접 참조
    @JoinColumn(name = "user_id", nullable = false)
    private Long userId;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "is_secret")
    private Boolean isSecret;

    @Column(name = "like_count", nullable = false, columnDefinition = "integer default 0")
    private int likeCount;

    // 2. 명확한 목적의 update 메서드 사용
    public void update(CommentRequestDto requestDto) {
        this.content = requestDto.getContent();
        this.isSecret = requestDto.getIsSecret();
    }


}
