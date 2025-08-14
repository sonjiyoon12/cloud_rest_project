package com.cloud.cloud_rest.board;

import com.cloud.cloud_rest.Comment.Comment;
import com.cloud.cloud_rest.board.board_tag.BoardTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BoardResponseDto {

    @Getter
    @NoArgsConstructor
    public static class ListDto {
        private Long id;
        private String title;
        private String authorName;
        private Integer viewCount;
        private Integer likeCount;
        private LocalDateTime createdAt;
        private List<String> tags;

        public ListDto(Board board) {
            this.id = board.getBoardId();
            this.title = board.getTitle();
            this.authorName = board.getUser().getUsername();
            this.viewCount = board.getViews();
            this.likeCount = board.getLikeCount();
            this.createdAt = board.getCreatedAt();
            this.tags = board.getTags().stream().map(BoardTag::getName).collect(Collectors.toList());
        }

        // List<Board>를 List<ListDto>로 변환하는 정적 메서드 추가
        public static List<ListDto> toDtoList(List<Board> boards) {
            return boards.stream()
                    .map(ListDto::new)
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @Setter
    public static class DetailDto {
        private Long id;
        private String title;
        private String content;
        private String authorName;
        private Integer viewCount;
        private Integer likeCount;
        private LocalDateTime createdAt;
        private List<String> tags;
        private List<Comment> comments;

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