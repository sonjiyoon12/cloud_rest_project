package com.cloud.cloud_rest.qnaAnswer;

import com.cloud.cloud_rest.qnaBoard.QnaBoard;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

public class QnaAnswerRequest {

    // qna 답변 저장
    @Schema(name = "qnaAnswerSaveRequest")
    @Data
    public static class SaveDTO {
        @NotBlank(message = "내용을 입력해주세요")
        @Size(max = 2000, message = "내용은 2000자 이내로 작성해주세요")
        private String content;

       public QnaAnswer toEntity(QnaBoard qnaBoard){
           return QnaAnswer.builder()
                   .content(this.content)
                   .qnaBoard(qnaBoard)
                   .build();
       }
    }

    // qna 답변 수정
    @Schema(name = "qnaAnswerUpdateRequest")
    @Data
    public static class UpdateDTO{
        @NotBlank(message = "내용을 입력해주세요")
        @Size(max = 2000, message = "내용은 2000자 이내로 작성해주세요")
        private String content;
    }
}
