package com.cloud.cloud_rest.rate.user_rate;

import lombok.Builder;
import lombok.Data;

public class UserRateResponse {

    @Data
    public static class SaveDTO {
        private Long id;
        private Long userId;
        private Long corpId;
        private Long rating;
        private String createdAt;

        @Builder
        public SaveDTO(UserRate userRate) {
            this.id = userRate.getUserRateId();
            this.userId = userRate.getUser().getUserId();
            this.corpId = userRate.getCorp().getCorpId();
            this.rating = userRate.getRating();
            this.createdAt = userRate.getTime();
        }
    }

    @Data
    public static class DetailDTO {
        private Long id;
        private Long userId;
        private Long corpId;
        private Long rating;
        private String createdAt;

        @Builder
        public DetailDTO(UserRate userRate) {
            this.id = userRate.getUserRateId();
            this.userId = userRate.getUser().getUserId();
            this.corpId = userRate.getCorp().getCorpId();
            this.rating = userRate.getRating();
            this.createdAt = userRate.getTime();
        }
    }
}
