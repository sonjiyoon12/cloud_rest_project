package com.cloud.cloud_rest.noti;

import com.cloud.cloud_rest.recruit.Recruit;
import com.cloud.cloud_rest.user.User;
import lombok.Data;

public class NotiRequest {

    @Data
    public static class SaveDTO {

        public Noti toEntity(Recruit recruit, User user, String message) {
            return Noti.builder()
                    .recruit(recruit)
                    .user(user)
                    .isRead(false)
                    .message(message)
                    .build();
        }
    }
}
