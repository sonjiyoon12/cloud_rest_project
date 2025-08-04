package com.cloud.cloud_rest.corp;

import com.cloud.cloud_rest.user.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class CorpRequest {

    @Data
    public static class SaveDTO{
        private String corpName;
        private String loginId;
        private String password;
        private String rePassword;
        private String email;

        public Corp toEntity(String encoderPassword){
            return Corp.builder()
                    .corpName(corpName)
                    .loginId(loginId)
                    .password(encoderPassword)
                    .email(email)
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
        private String email;
        private MultipartFile corpImage; // 웹에서 받을때
        
        private String corpImageBase64; // 모바일 / 외부 API
    }

}
