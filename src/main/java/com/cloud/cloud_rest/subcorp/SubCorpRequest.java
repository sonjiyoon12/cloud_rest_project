package com.cloud.cloud_rest.subcorp;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class SubCorpRequest {

    @Schema(name = "SubCorpSaveRequest")
    @Data
    public static class SaveDTO {
        private Long corpId;
        private Long userId;

        public SubCorp toEntity(User user, Corp corp) {
            return SubCorp.builder()
                    .user(user)
                    .corp(corp)
                    .build();
        }
    }
}
