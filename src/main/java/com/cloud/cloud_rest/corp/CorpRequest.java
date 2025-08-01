package com.cloud.cloud_rest.corp;

import com.cloud.cloud_rest.user.User;
import lombok.Data;

public class CorpRequest {

    @Data
    public static class SaveDTO{
        private String corpName;
        private String loginId;
        private String password;
        private String email;

        public Corp toEntity(String corpUploadImage){
            return Corp.builder()
                    .corpName(corpName)
                    .loginId(loginId)
                    .password(password)
                    .email(email)
                    .corpImage(corpUploadImage)
                    .build();
        }
    }

    @Data
    public static class LoginDTO{
        private String loginId;
        private String password;
    }

    @Data
    public static class UpdateDTO{
        private String corpName;
        private String corpImage;
    }

}
