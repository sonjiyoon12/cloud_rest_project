package com.cloud.cloud_rest.board;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class BoardRequestDto {

    @Data
    public static class SaveDto {
        private String title;
        private String content;
        private Long userId;
        private String base64Image;
        private String imagePath;

        public Board toEntity(String imagePath) {
            return Board.builder()
                    .title(title)
                    .content(content)
                    .userId(userId)
                    .views(0) // 새 게시물이니까 조회수를 0 으로 초기 설정
                    .likeCount(0) // 새 게시물이니까 좋아요 수 0 으로 초기 설정
                    .imagePath(imagePath)
                    .build();
        }
    }

    @Data
    public static class UpdateDto {
        private String title;
        private String content;
        private String imagePathBase64; // JSON 형식으로 파일 받기
        private MultipartFile imagePath; // Multipart 로 파일 받기
    }


}
