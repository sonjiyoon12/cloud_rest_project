package com.cloud.cloud_rest.corp_approval;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpStatus;
import com.cloud.cloud_rest.user.User;
import lombok.Data;

public class CorpApprovalRequest {

    @Data
    public static class ApprovalDTO{
        private CorpStatus corpStatus;
        private String text;

        public CorpApproval toEntity(Corp corp,User user){
            return CorpApproval.builder()
                    .corp(corp)
                    .corpStatus(corpStatus)
                    .text(text)
                    .user(user)
                    .build();
        }
    }

}
