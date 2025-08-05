package com.cloud.cloud_rest.rate.corp_rate;

import lombok.Builder;
import lombok.Data;

public class CorpRateResponse {

    @Data
    public static class SaveDTO {
        private Long id;
        private Long userId;
        private Long corpId;
        private Long rating;
        private Boolean isBlocked;
        private String createdAt;

        @Builder
        public SaveDTO(CorpRate corpRate) {
            this.id = corpRate.getCorpRateId();
            this.userId = corpRate.getUser().getUserId();
            this.corpId = corpRate.getCorp().getCorpId();
            this.rating = corpRate.getRating();
            this.isBlocked = corpRate.getIsBlocked();
            this.createdAt = corpRate.getTime();
        }
    }

    @Data
    public static class DetailDTO {
        private Long id;
        private Long userId;
        private Long corpId;
        private Long rating;
        private Boolean isBlocked;
        private String createdAt;

        @Builder
        public DetailDTO(CorpRate corpRate) {
            this.id = corpRate.getCorpRateId();
            this.userId = corpRate.getUser().getUserId();
            this.corpId = corpRate.getCorp().getCorpId();
            this.rating = corpRate.getRating();
            this.isBlocked = corpRate.getIsBlocked();
            this.createdAt = corpRate.getTime();
        }
    }
}
