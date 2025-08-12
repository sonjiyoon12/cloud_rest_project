package com.cloud.cloud_rest.Comment;

import com.cloud.cloud_rest._global._core.common.Timestamped;
import com.cloud.cloud_rest.board.Board;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.CommentLike.CommentLike;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board_comment_tb")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    @ToString.Exclude
    @JsonBackReference
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "is_secret")
    private Boolean isSecret;

    @Column(name = "like_count", nullable = false, columnDefinition = "integer default 0")
    private int likeCount;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentLike> likes = new ArrayList<>();

    @Builder
    public Comment(Board board, User user, String content, Boolean isSecret) {
        this.board = board;
        this.user = user;
        this.content = content;
        this.isSecret = isSecret != null && isSecret;
        this.likeCount = 0;
    }

    // 2. 명확한 목적의 update 메서드 사용
    public void update(CommentRequestDto.UpdateDto requestDto) {
        this.content = requestDto.getContent();
        this.isSecret = requestDto.isSecret();
    }

    // likeCount를 안전하게 변경하기 위한 메서드
    public void updateLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }
}
