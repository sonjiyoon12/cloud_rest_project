package com.cloud.cloud_rest.resume;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest.user.User;
import lombok.Builder;
import lombok.Data;

public class ResumeResponse {

    // 이력서 전체보기 응답
    @Data
    public static class ListDTO {
        private Long resumeId;
        private String title;
        private String content;
        private String writerName;
        private String createdAt;
        private boolean isRep;

        public ListDTO(Resume resume) {
            this.resumeId = resume.getResumeId();
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.writerName = resume.getUser().getUsername();
            this.createdAt = resume.getCreatedAt().toString();
            this.isRep = resume.getIsRep();
        }
    }

    // 이력서 상세보기 응답
    @Data
    public static class DetailDTO {
        private Long resumeId;
        private String title;
        private String content;
        private String writerName;
        private String createdAt;
        private boolean isResumeOwner;

        public DetailDTO(Resume resume, SessionUser sessionUser) {
            this.resumeId = resume.getResumeId();
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.writerName = resume.getUser().getUsername();
            this.createdAt = resume.getCreatedAt().toString();
             this.isResumeOwner = sessionUser != null && resume.isOwner(sessionUser.getId());
        }
    }

    // 이력서 작성 응답
    @Data
    public static class SaveDTO {
        private Long resumeId;
        private String title;
        private String content;
        private String writerName;
        private String createdAt;

        @Builder
        public SaveDTO(Resume resume) {
            this.resumeId = resume.getResumeId();
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.writerName = resume.getUser().getUsername();
            this.createdAt = resume.getCreatedAt().toString();
        }
    }

    // 이력서 수정 응답
    @Data
    public static class UpdateDTO {
        private Long resumeId;
        private String title;
        private String content;
        private String writerName;
        private String createdAt;

        @Builder
        public UpdateDTO(Resume resume) {
            this.resumeId = resume.getResumeId();
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.writerName = resume.getUser().getUsername();
            this.createdAt = resume.getCreatedAt().toString();
        }
    }
}
