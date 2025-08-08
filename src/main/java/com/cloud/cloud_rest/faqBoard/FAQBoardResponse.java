package com.cloud.cloud_rest.faqBoard;

import com.cloud.cloud_rest.resume.ResumeResponse;
import lombok.Data;

public class FAQBoardResponse {

    // 전체조회 응답
    @Data
    public static class ListDTO {
        private Long faqId;
        private String title;
        private String writerName;
        private String createdAt;

        public ListDTO(FAQBoard faqBoard) {
            this.faqId = faqBoard.getFaqId();
            this.title = faqBoard.getTitle();
            this.writerName = faqBoard.getUser().getUsername();
            this.createdAt = faqBoard.getFormatTime();
        }
    }

    // 상세보기 응답
    @Data
    public static class DetailDTO{
        private Long faqId;
        private String title;
        private String content;
        private String writerName;
        private String createdAt;
        private String answer;

        public DetailDTO(FAQBoard faqBoard, String answer) {
            this.faqId = faqBoard.getFaqId();
            this.title = faqBoard.getTitle();
            this.content = faqBoard.getContent();
            this.writerName = faqBoard.getUser().getUsername();
            this.createdAt = faqBoard.getFormatTime();
            this.answer = answer;
        }
    }

    // 작성, 수정 응답
    @Data
    public static class FaqResponseDTO {
        private Long faqId;
        private String title;
        private String content;
        private String writerName;
        private String createdAt;

        public FaqResponseDTO(FAQBoard faqBoard) {
            this.faqId = faqBoard.getFaqId();
            this.title = faqBoard.getTitle();
            this.content = faqBoard.getContent();
            this.writerName = faqBoard.getUser().getUsername();
            this.createdAt = faqBoard.getFormatTime();
        }
    }

}
