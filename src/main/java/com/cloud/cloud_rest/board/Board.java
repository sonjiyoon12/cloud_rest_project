package com.cloud.cloud_rest.board;

import com.cloud.cloud_rest.Comment.Comment;
import com.cloud.cloud_rest.Like.BoardLike;
import com.cloud.cloud_rest._global._core.common.Timestamped;
import com.cloud.cloud_rest.board.board_tag.BoardTag;
import com.cloud.cloud_rest.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board_tb")
@Builder
public class Board extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    @Column(name = "views", columnDefinition = "int default 0")
    private Integer views;

    @Column(name = "like_count", columnDefinition = "int default 0")
    private Integer likeCount;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @ToString.Exclude
    private List<Comment> comments;

    private String imagePath;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<BoardLike> likes = new ArrayList<>();

    public void update(BoardRequestDto.UpdateDto updateDTO, String imagePath) {
        this.title = updateDTO.getTitle();
        this.content = updateDTO.getContent();
        this.imagePath = imagePath;
    }

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<BoardTag> tags = new ArrayList<>();

    @Builder
    public Board(Long boardId, String title, String content, User user, Integer views, Integer likeCount, List<Comment> comments, String imagePath, List<BoardLike> likes, List<BoardTag> tags) {
        this.boardId = boardId;
        this.title = title;
        this.content = content;
        this.user = user;
        this.views = views;
        this.likeCount = likeCount;
        this.comments = comments;
        this.imagePath = imagePath;
        this.likes = likes;
        this.tags = tags;
    }

    public void increaseViewCount() {
        this.views++;
    }

    public void updateLikeCount(int count) { this.likeCount = count; }

    public void addTag(BoardTag tag) { this.tags.add(tag); }

    public void clearTags() { this.tags.clear(); }
}
