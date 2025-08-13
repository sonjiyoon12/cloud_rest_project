package com.cloud.cloud_rest.rate.corp_rate;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

public class CorpRateRequest {

    @Schema(name = "corpRateSaveRequest")
    @Data
    public static class SaveDTO {

        private Long userId;

        @Min(value = 1, message = "평점은 1점 이상 입력해주세요.")
        @Max(value = 5, message = "평점은 5점 이하로 입력해주세요.")
        private Long rating;

        public CorpRate toEntity(Corp corp, User user) {
            return CorpRate.builder()
                    .corp(corp)
                    .user(user)
                    .rating(this.rating == null ? 1 : this.rating)
                    .build();
        }
    }
}
