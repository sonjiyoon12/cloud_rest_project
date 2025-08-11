package com.cloud.cloud_rest.qnaBoard;

import lombok.Data;

public class QnaBoardResponse {

    // 전체조회 응답
    @Data
    public static class ListDTO {
        private Long qnaBoardId;
        private String title;
        private String writerName;
        private String createdAt;

        public ListDTO(QnaBoard qnaBoard) {
            this.qnaBoardId = qnaBoard.getQnaBoardId();
            this.title = qnaBoard.getTitle();
            this.writerName = qnaBoard.getUser().getUsername();
            this.createdAt = qnaBoard.getTime();
        }
    }

    // 상세보기 응답
    @Data
    public static class DetailDTO{
        private Long qnaBoardId;
        private String title;
        private String content;
        private String writerName;
        private String createdAt;
        private String answer;

        public DetailDTO(QnaBoard qnaBoard, String answer) {
            this.qnaBoardId = qnaBoard.getQnaBoardId();
            this.title = qnaBoard.getTitle();
            this.content = qnaBoard.getContent();
            this.writerName = qnaBoard.getUser().getUsername();
            this.createdAt = qnaBoard.getTime();
            this.answer = answer;
        }
    }

    // 작성, 수정 응답
    @Data
    public static class QnaBoardResponseDTO {
        private Long qnaBoardId;
        private String title;
        private String content;
        private String writerName;
        private String createdAt;

        public QnaBoardResponseDTO(QnaBoard qnaBoard) {
            this.qnaBoardId = qnaBoard.getQnaBoardId();
            this.title = qnaBoard.getTitle();
            this.content = qnaBoard.getContent();
            this.writerName = qnaBoard.getUser().getUsername();
            this.createdAt = qnaBoard.getTime();
        }
    }

}
