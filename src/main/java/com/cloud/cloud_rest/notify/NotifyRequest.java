package com.cloud.cloud_rest.notify;

import com.cloud.cloud_rest.recruit.Recruit;
import com.cloud.cloud_rest.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class NotifyRequest {

    @Data
    @Schema(name = "notifySaveRequest")
    public static class SaveDTO {

        public Notify toEntity(Recruit recruit, User user, String message) {
            return Notify.builder()
                    .recruit(recruit)
                    .user(user)
                    .isRead(false)
                    .message(message)
                    .build();
        }
    }
}
