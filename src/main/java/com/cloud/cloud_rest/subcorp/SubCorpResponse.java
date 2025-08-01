package com.cloud.cloud_rest.subcorp;


import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
public class SubCorpResponse {

    @Data
    public static class SubCorpResponseDTO {

        private Long corpId;
        private String corpName;
        private String corpImage;

        public SubCorpResponseDTO(Corp corp) {
            this.corpId = corp.getCorpId();
            this.corpName = corp.getCorpName();
            this.corpImage = corp.getCorpImage();
        }


    }


    public static class SubCorpCheckDTO {
        private boolean isSubscribed;
    }


}

