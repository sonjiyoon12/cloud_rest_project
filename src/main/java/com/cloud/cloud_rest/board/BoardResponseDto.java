package com.cloud.cloud_rest.board;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class BoardResponseDto {

    // 게시글 하나의 상세 정보를 응답하는 Dto
    @Data
    public static class DetailDto {
        private Long boardId;
        private String title;
        private String content;
        private Long userId;
        private LocalDateTime createdAt;
        private Integer views;

        @Builder
        public DetailDto(Board board) {
            this.boardId = board.getBoardId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.userId = board.getUserId();
            this.createdAt = board.getCreatedAt();
            this.views = board.getViews();
        }
    }

    // 게시글 목록을 응답하는 Dto
    @Data
    public static class ListDto {
        private Long boardId;
        private String title;
        private Long userId;
        private LocalDateTime createdAt;
        private Integer views;

        public ListDto(Board board) {
            this.boardId = board.getBoardId();
            this.title = board.getTitle();
            this.userId = board.getUserId();
            this.createdAt = board.getCreatedAt();
            this.views = board.getViews();
        }

        // board 엔티티 리스트를 ListDto로 변환하는 정적 메서드
        public static List<ListDto> toList(List<Board> boards) {
            return boards.stream()
                    .map(ListDto::new)
                    .collect(Collectors.toUnmodifiableList());
        }
    }
}
