package com.cloud.cloud_rest.rate.corp_rate;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class CorpRateRequest {

    @Schema(name = "corpRateSaveRequest")
    @Data
    public static class SaveDTO {
        private Long rating;
        private Boolean isBlocked;

        public CorpRate toEntity(Corp sessionUser, User user) {
            return CorpRate.builder()
                    .corp(sessionUser)
                    .user(user)
                    .rating(this.rating)
                    .isBlocked(this.isBlocked)
                    .build();
        }
    }
}
