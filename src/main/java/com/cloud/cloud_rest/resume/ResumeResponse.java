package com.cloud.cloud_rest.resume;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest.career.CareerResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

public class ResumeResponse {

    // 이력서 전체조회 응답
    @Data
    public static class ListDTO {
        private Long resumeId;
        private String title;
        private String writerName;
        private String createdAt;
        private List<String> skills;

        public ListDTO(Resume resume) {
            this.resumeId = resume.getResumeId();
            this.title = resume.getTitle();
            this.writerName = resume.getUser().getUsername();
            this.createdAt = resume.getFormatTime();
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
        private String image;
        private List<String> skills;
        private List<CareerResponse.InfoDTO> careers;

        public DetailDTO(Resume resume, SessionUser sessionUser) {
            this.resumeId = resume.getResumeId();
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.writerName = resume.getUser().getUsername();
            this.createdAt = resume.getFormatTime();
            this.isResumeOwner = sessionUser != null && resume.isOwner(sessionUser.getId());
            this.isRep = resume.getIsRep();
            this.image = resume.getImage();
            this.skills = resume.getResumeSkills().stream()
                    .map(resumeSkill -> resumeSkill.getSkill().getName())
                    .toList();
            this.careers = resume.getCareers().stream()
                    .map(CareerResponse.InfoDTO::new)
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
        private String image;
        private List<String> skills;
        private List<CareerResponse.InfoDTO> careers;

        @Builder
        public SaveDTO(Resume resume) {
            this.resumeId = resume.getResumeId();
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.writerName = resume.getUser().getUsername();
            this.createdAt = resume.getFormatTime();
            this.isRep = resume.getIsRep();
            this.image = resume.getImage();
            this.skills = resume.getResumeSkills().stream()
                    .map(resumeSkill -> resumeSkill.getSkill().getName())
                    .toList();
            this.careers = resume.getCareers().stream()
                    .map(CareerResponse.InfoDTO::new)
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
        private String image;
        private List<String> skills;
        private List<CareerResponse.InfoDTO> careers;

        @Builder
        public UpdateDTO(Resume resume) {
            this.resumeId = resume.getResumeId();
            this.title = resume.getTitle();
            this.content = resume.getContent();
            this.writerName = resume.getUser().getUsername();
            this.createdAt = resume.getFormatTime();
            this.isRep = resume.getIsRep();
            this.image = resume.getImage();
            this.skills = resume.getResumeSkills().stream()
                    .map(resumeSkill -> resumeSkill.getSkill().getName())
                    .toList();
            this.careers = resume.getCareers().stream()
                    .map(CareerResponse.InfoDTO::new)
                    .toList();
        }
    }
}
