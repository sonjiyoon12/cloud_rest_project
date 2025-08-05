package com.cloud.cloud_rest.resume;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest.user.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public class ResumeResponse {

    // 이력서 전체보기 응답
    @Data
    public static class ListDTO {
        private Long resumeId;
        private String title;
        private String content;
        private String writerName;
        private String createdAt;
        private List<String> skills;

        public ListDTO(Resume resume) {
            this.resumeId = resume.getResumeId();
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.writerName = resume.getUser().getUsername();
            this.createdAt = resume.getCreatedAt().toString();
            this.skills = resume.getResumeSkills().stream()
                    .map(resumeSkill -> resumeSkill.getSkill().getName())
                    .toList();
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
        private boolean isRep;
        private List<String> skills;

        public DetailDTO(Resume resume, SessionUser sessionUser) {
            this.resumeId = resume.getResumeId();
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.writerName = resume.getUser().getUsername();
            this.createdAt = resume.getCreatedAt().toString();
            this.isResumeOwner = sessionUser != null && resume.isOwner(sessionUser.getId());
            this.isRep = resume.getIsRep();
            this.skills = resume.getResumeSkills().stream()
                    .map(resumeSkill -> resumeSkill.getSkill().getName())
                    .toList();
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
        private boolean isRep;
        private List<String> skills;

        @Builder
        public SaveDTO(Resume resume) {
            this.resumeId = resume.getResumeId();
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.writerName = resume.getUser().getUsername();
            this.createdAt = resume.getCreatedAt().toString();
            this.isRep = resume.getIsRep();
            this.skills = resume.getResumeSkills().stream()
                    .map(resumeSkill -> resumeSkill.getSkill().getName())
                    .toList();
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
        private boolean isRep;
        private List<String> skills;

        @Builder
        public UpdateDTO(Resume resume) {
            this.resumeId = resume.getResumeId();
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.writerName = resume.getUser().getUsername();
            this.createdAt = resume.getCreatedAt().toString();
            this.isRep = resume.getIsRep();
            this.skills = resume.getResumeSkills().stream()
                    .map(resumeSkill -> resumeSkill.getSkill().getName())
                    .toList();
        }
    }
}
