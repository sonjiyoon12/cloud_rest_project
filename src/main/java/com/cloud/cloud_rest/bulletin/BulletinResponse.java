package com.cloud.cloud_rest.bulletin;

import com.cloud.cloud_rest._global.utils.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

public class BulletinResponse {

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DTO {
        private Long id;
        private String title;
        private String authorName;
        private String imagePath; // 필드 추가
        private Timestamp createdAt;

        public String getFormatTime() {
            return DateUtil.timestampFormat(createdAt);
        }

        public DTO(Bulletin bulletin) {
            this.id = bulletin.getBulletinId(); // getBulletinId()로 수정
            this.title = bulletin.getTitle();
            this.authorName = bulletin.getUser().getUsername();
            this.imagePath = bulletin.getImagePath(); // imagePath 추가
            this.createdAt = bulletin.getCreatedAt();
        }
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailDTO {
        private Long id;
        private String title;
        private String content;
        private String authorName;
        private Long userId;
        private String imagePath; // 필드 추가
        private Timestamp createdAt;

        public String getFormatTime() {
            return DateUtil.timestampFormat(createdAt);
        }

        public DetailDTO(Bulletin bulletin) {
            this.id = bulletin.getBulletinId(); // getBulletinId()로 수정
            this.title = bulletin.getTitle();
            this.content = bulletin.getContent();
            this.authorName = bulletin.getUser().getUsername();
            this.userId = bulletin.getUser().getUserId();
            this.imagePath = bulletin.getImagePath(); // imagePath 추가
            this.createdAt = bulletin.getCreatedAt();
        }
    }
}
