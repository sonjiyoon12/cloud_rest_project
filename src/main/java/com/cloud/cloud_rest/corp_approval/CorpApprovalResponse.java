package com.cloud.cloud_rest.corp_approval;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpResponse;
import com.cloud.cloud_rest.corp.CorpStatus;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserResponse;
import lombok.Builder;
import lombok.Data;

public class CorpApprovalResponse {

    @Data
    public static class ApprovalDTO {
        private Long corpApprovalId;
        private CorpResponse.CorpDTO corp;
        private UserResponse.UserDTO user;
        private String text;
        private String corpStatus;
        private String createdAt;

        @Builder
        public ApprovalDTO(CorpApproval corpApproval) {
            this.corpApprovalId = corpApproval.getCorpApprovalId();
            this.corp = new CorpResponse.CorpDTO(corpApproval.getCorp());
            this.user = new UserResponse.UserDTO(corpApproval.getUser());
            this.text = corpApproval.getText();
            this.corpStatus = corpApproval.getCorpStatus().name();
            this.createdAt = corpApproval.getTime();
        }
    }
}

