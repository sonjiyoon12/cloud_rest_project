package com.cloud.cloud_rest.corp;

import lombok.Builder;
import lombok.Data;

public class CorpResponse {

    // Corp 상세 정보
    @Data
    public static class CorpDTO{
        private Long corpId;
        private String corpName;
        private String email;
        private String corpImage;
        private String createdAt;

        @Builder
        public CorpDTO(Corp corp){
            this.corpId = corp.getCorpId();
            this.corpName = corp.getCorpName();
            this.email = corp.getEmail();
            this.corpImage = corp.getCorpImage();
            this.createdAt = corp.getCreatedAt().toString();
        }
    }


    // Corp 로그인 응답 DTO
    @Data
    public static class LoginDTO{
        private String loginId;
        private String corpName;

        @Builder
        public LoginDTO(Corp corp){
            this.loginId = corp.getLoginId();
            this.corpName = corp.getCorpName();
        }
    }

    // Corp 업데이트 응답 DTO
    @Data
    public static class UpdateDTO{
        private String corpName;
        private String corpImage;

        @Builder
        public UpdateDTO(Corp corp,String corpUploadImage){
            this.corpName = corp.getCorpName();
            this.corpImage = corpUploadImage;
        }
    }

}
