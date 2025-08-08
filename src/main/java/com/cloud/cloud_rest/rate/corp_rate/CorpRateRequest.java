package com.cloud.cloud_rest.rate.corp_rate;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class CorpRateRequest {

    @Schema(name = "corpRateSaveRequest")
    @Data
    public static class SaveDTO {

        @Size(min = 1, max = 5, message = "평점은 1~5점 사이로 입력해주세요.")
        private Long rating;

        public CorpRate toEntity(Corp sessionUser, User user) {
            return CorpRate.builder()
                    .corp(sessionUser)
                    .user(user)
                    .rating(this.rating == null ? 1 : this.rating)
                    .build();
        }
    }
}
