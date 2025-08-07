package com.cloud.cloud_rest.subcorp;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.User;
import lombok.Data;

public class SubCorpRequest {

    @Data
    public static class SaveDTO {
        private Corp corp;

        public SubCorp toEntity(User user, Corp corp) {
            return SubCorp.builder()
                    .user(user)
                    .corp(corp)
                    .build();
        }
    }
}
