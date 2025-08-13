package com.cloud.cloud_rest.corp_approval;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpStatus;
import com.cloud.cloud_rest.user.User;
import lombok.Builder;
import lombok.Data;

public class CorpApprovalResponse {

    @Data
    public static class ApprovalDTO{
        private Long corpApprovalId;
        private Corp corp;
        private User user;
        private String text;
        private CorpStatus corpStatus;
        private String createdAt;

        @Builder
        public ApprovalDTO (CorpApproval corpApproval){
            this.corpApprovalId = corpApproval.getCorpApprovalId();
            this.corp = corpApproval.getCorp();
            this.user = corpApproval.getUser();
            this.text = corpApproval.getText();
            this.corpStatus = corpApproval.getCorpStatus();
            this.createdAt = getCreatedAt();
        }
    }
}
