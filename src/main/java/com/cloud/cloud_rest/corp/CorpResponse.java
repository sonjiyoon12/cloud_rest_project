package com.cloud.cloud_rest.corp;

import lombok.Builder;
import lombok.Data;

public class CorpResponse {

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

    @Data
    public static class SaveDTO{

    }

}
