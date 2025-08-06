package com.cloud.cloud_rest.Like;

import com.cloud.cloud_rest.board.Board;
import com.cloud.cloud_rest.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Table(name = "board_like_tb")
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_like_id")
    private Long boardLikeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    private User user;

    @CreatedDate
    @Column(name = "liked_at", updatable = false)
    private LocalDateTime likedAt;

    @Column(name = "is_owner", nullable = false)
    private boolean isOwner;

    @Builder
    public BoardLike(Board board, User user, boolean isOwner) {
        this.board = board;
        this.user = user;
        this.isOwner = isOwner;
    }
}