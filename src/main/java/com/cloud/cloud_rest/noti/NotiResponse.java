package com.cloud.cloud_rest.noti;

import com.cloud.cloud_rest.recruit.Recruit;
import lombok.Data;

import java.time.LocalDate;

public class NotiResponse {

    @Data
    public static class DetailDTO {
        private Long notiId;
        private Boolean isRead;
        private Long userId;
        private String message;
        private String createdAt;
        private RecruitDTO recruit;

        public DetailDTO(Noti noti) {
            this.notiId = noti.getNotificationId();
            this.isRead = noti.getIsRead();
            this.userId = noti.getUser().getUserId();
            this.message = noti.getMessage();
            this.createdAt = noti.getTime();
            this.recruit = new RecruitDTO(noti.getRecruit());
        }

        @Data
        public static class RecruitDTO {
            private Long recruitId;
            private Long corpId;
            private String title;
            private String content;
            private LocalDate deadline;
            private String createdAt;

            public RecruitDTO(Recruit recruit) {
                this.recruitId = recruit.getRecruitId();
                this.corpId = recruit.getCorp().getCorpId();
                this.title = recruit.getTitle();
                this.content = recruit.getContent();
                this.deadline = recruit.getDeadline();
                this.createdAt = recruit.getCreatedAt().toString();
            }
        }
    }
}
