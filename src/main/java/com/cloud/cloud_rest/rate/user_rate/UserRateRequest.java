package com.cloud.cloud_rest.rate.user_rate;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

public class UserRateRequest {

    @Schema(name = "userRateSaveRequest")
    @Data
    public static class SaveDTO {

        private Long corpId;

        @Min(value = 1, message = "평점은 1점 이상 입력해주세요.")
        @Max(value = 5, message = "평점은 5점 이하로 입력해주세요.")
        private Long rating;

        public UserRate toEntity(User user, Corp corp) {
            return UserRate.builder()
                    .user(user)
                    .corp(corp)
                    .rating(this.rating)
                    .build();
        }
    }
}
