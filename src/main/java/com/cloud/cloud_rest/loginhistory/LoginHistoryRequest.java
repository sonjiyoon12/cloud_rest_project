package com.cloud.cloud_rest.loginhistory;

import com.cloud.cloud_rest.user.User;
import lombok.Data;

public class LoginHistoryRequest {

    @Data
    public static class SaveDTO{
        private User user;
        private String ipAddress;
        private String userAgent;

        public LoginHistory toEntity(User user,String ipAddress,String userAgent){
            return LoginHistory.builder()
                    .user(user)
                    .ipAddress(ipAddress)
                    .userAgent(userAgent)
                    .build();
        }
    }
}
