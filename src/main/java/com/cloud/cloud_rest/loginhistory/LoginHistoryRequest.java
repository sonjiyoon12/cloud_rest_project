package com.cloud.cloud_rest.loginhistory;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.User;
import lombok.Data;

public class LoginHistoryRequest {

    @Data
    public static class SaveUserDTO {
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

    @Data
    public static class SaveCorpDTO{
        private Corp corp;
        private String ipAddress;
        private String userAgent;

        public LoginHistory toEntity(Corp corp, String ipAddress, String userAgent){
            return LoginHistory.builder()
                    .corp(corp)
                    .ipAddress(ipAddress)
                    .userAgent(userAgent)
                    .build();
        }
    }
}
