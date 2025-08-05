package com.cloud.cloud_rest.rate.user_rate;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class UserRateRequest {

    @Schema(name = "userRateSaveRequest")
    @Data
    public static class SaveDTO {
        private Long rating;

        public UserRate toEntity(User sessionUser, Corp corp) {
            return UserRate.builder()
                    .user(sessionUser)
                    .corp(corp)
                    .rating(this.rating)
                    .build();
        }
    }
}
