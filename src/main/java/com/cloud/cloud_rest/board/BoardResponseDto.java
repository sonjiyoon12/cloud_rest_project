package com.cloud.cloud_rest.board;

import com.cloud.cloud_rest.Comment.Comment;
import com.cloud.cloud_rest.board.board_tag.BoardTag;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BoardResponseDto {

    @Getter
    public static class ListDto {
        private final Long id;
        private final String title;
        private final String authorName;
        private final Integer viewCount;
        private final Integer likeCount;
        private final LocalDateTime createdAt;
        private final List<String> tags;

        public ListDto(Board board) {
            this.id = board.getBoardId();
            this.title = board.getTitle();
            this.authorName = board.getUser().getUsername();
            this.viewCount = board.getViews();
            this.likeCount = board.getLikeCount();
            this.createdAt = board.getCreatedAt();
            this.tags = board.getTags().stream().map(BoardTag::getName).collect(Collectors.toList());
        }
    }

    @Getter
    public static class DetailDto {
        private final Long id;
        private final String title;
        private final String content;
        private final String authorName;
        private final Integer viewCount;
        private final Integer likeCount;
        private final LocalDateTime createdAt;
        private final List<String> tags;
        private final List<Comment> comments;

        public DetailDto(Board board) {
            this.id = board.getBoardId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.authorName = board.getUser().getUsername();
            this.viewCount = board.getViews();
            this.likeCount = board.getLikeCount();
            this.createdAt = board.getCreatedAt();
            this.tags = board.getTags().stream().map(BoardTag::getName).collect(Collectors.toList());
            this.comments = board.getComments();
        }
    }
}