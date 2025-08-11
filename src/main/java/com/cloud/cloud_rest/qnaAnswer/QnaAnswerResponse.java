package com.cloud.cloud_rest.qnaAnswer;

import lombok.Data;

public class QnaAnswerResponse {

    @Data
    public static class QnaAnswerResponseDTO {
        private Long qnaAnswerId;
        private String content;
        private String createdAt;

        public QnaAnswerResponseDTO(QnaAnswer qnaAnswer) {
            this.qnaAnswerId = qnaAnswer.getQnaAnswerId();
            this.content = qnaAnswer.getContent();
            this.createdAt = qnaAnswer.getTime();
        }
    }
}
