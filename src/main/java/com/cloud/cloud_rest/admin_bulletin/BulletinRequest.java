package com.cloud.cloud_rest.admin_bulletin;

import com.cloud.cloud_rest.user.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

public class BulletinRequest {

    @Getter
    @Setter
    public static class SaveDTO {
        @NotEmpty
        private String title;

        @NotEmpty
        private String content;

        private String base64Image;

        public Bulletin toEntity(User user, String imagePath) {
            return Bulletin.builder()
                    .title(this.title)
                    .content(this.content)
                    .imagePath(imagePath)
                    .user(user)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class UpdateDTO {
        @NotEmpty
        private String title;

        @NotEmpty
        private String content;

        private String imagePath;
    }
}
