package com.cloud.cloud_rest.board;

import lombok.*;
import lombok.Builder;

public class BoardRequestDto {

    @Data
    public static class SaveDto{
        private String title;
        private String content;
        private Long userId;

        @Builder
        public Board toEntity() {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .userId(userId)
                    .views(0) // 새 게시물이니까 조회수를 0 으로 초기 설정
                    .likeCount(0) // 새 게시물이니까 좋아요 수 0 으로 초기 설정
                    .build();
        }
    }

    @Data
    public static class UpdateDto{
        private String title;
        private String content;
    }





}
