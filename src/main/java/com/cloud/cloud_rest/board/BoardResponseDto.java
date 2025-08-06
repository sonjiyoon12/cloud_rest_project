package com.cloud.cloud_rest.board;

import com.cloud.cloud_rest.Comment.CommentResponseDto;
import com.cloud.cloud_rest.board.board_tag.BoardTag;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        private String imagePath;
        private List<CommentResponseDto> comments;



        @Builder
        public DetailDto(Board board) {
            this.boardId = board.getBoardId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.userId = board.getUser().getUserId();
            this.createdAt = board.getCreatedAt();
            this.views = board.getViews();
            this.imagePath = board.getImagePath();
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
        private String imagePath;
        private List<TagDTO> tags = new ArrayList<>();

        public ListDto(Board board) {
            this.boardId = board.getBoardId();
            this.title = board.getTitle();
            this.userId = board.getUser().getUserId();
            this.createdAt = board.getCreatedAt();
            this.views = board.getViews();
            this.imagePath = board.getImagePath();

            for (BoardTag tag : board.getTags()) {
                this.tags.add(new TagDTO(tag));
            }
        }

        // board 엔티티 리스트를 ListDto로 변환하는 정적 메서드
        public static List<ListDto> toList(List<Board> boards) {
            return boards.stream()
                    .map(ListDto::new)
                    .toList();
        }

        @Data
        public static class TagDTO {
            private Long id;
            private Long boardId;
            private String tagName;

            public TagDTO(BoardTag boardTag) {
                this.id = boardTag.getBoardTagId();
                this.boardId = boardTag.getBoard().getBoardId();
                this.tagName = boardTag.getTagName();
            }
        }
    }

    // 게시글 수정 완료 후 응답하는 Dto
    @Data
    public static class UpdateDto {
        private Long boardId;
        private String title;
        private String content;
        private Long userId;
        private LocalDateTime createAt;
        private Integer views;
        private Integer likeCount;
        private String imagePath;

        @Builder
        public UpdateDto(Board board) {
            this.boardId = board.getBoardId();
            this.title = board.getTitle();
            this.content = board.getContent();
            this.userId = board.getUser().getUserId();
            this.createAt = board.getCreatedAt();
            this.views = board.getViews();
            this.imagePath = board.getImagePath();
        }
    }
}
