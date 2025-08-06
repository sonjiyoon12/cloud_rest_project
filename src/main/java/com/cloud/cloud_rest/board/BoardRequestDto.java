package com.cloud.cloud_rest.board;

import com.cloud.cloud_rest.user.User;
import lombok.Data;

import java.util.List;

public class BoardRequestDto {

    // 게시글 작성 요청 DTO
    @Data
    public static class SaveDto {
        private String title;
        private String content;
        private Long userId;
        private String base64Image;
        private List<String> boardTags;

        public Board toEntity(User user, String imagePath) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .user(user)
                    .views(0)
                    .likeCount(0)
                    .imagePath(imagePath)
                    .build();
        }
    }

    // 게시글 수정 요청 DTO
    @Data
    public static class UpdateDto {
        private String title;
        private String content;
        private Long userId;
        private String imagePathBase64;
    }

    // 태그 검색용 DTO
    @Data
    public static class SearchDTO {
        private String keyword; // 기존의 'search' 파라미터를 여기로 통합
        private List<String> boardTags;

        // --- Helper Methods ---
        public boolean hasKeyword() {
            return keyword != null && !keyword.trim().isEmpty();
        }
        public boolean hasTags() {
            return boardTags != null && !boardTags.isEmpty();
        }
    }

    @Data
    public static class FilterOptionDTO {
        private String name;    // 옵션의 이름
        private boolean checked; // 현재 선택되었는지 여부
    }
}