package com.cloud.cloud_rest.report;

import com.cloud.cloud_rest.board.Board;
import com.cloud.cloud_rest.user.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ReportRequestDto {

    /**
     * 신고 생성 요청을 위한 DTO
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Create {
        @NotNull(message = "신고 유형을 선택 해주세요")
        private ReportType reportType;

        @NotBlank(message = "신고 사유를 입력 해주세요")
        private String reason;


        public Report toEntity(Board board,User reporter) {
            return Report.builder()
                    .reportType(reportType)
                    .board(board)
                    .reason(reason)
                    .reporter(reporter)
                    .status(ReportStatus.PENDING)
                    .build();
        }
    }

    /**
     * 신고 상태 업데이트 요청을 위한 DTO (관리자용)
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Update {
        @NotNull(message = "신고 처리 상태를 지정 해주세요")
        private ReportStatus status;
        private String reason;

    }
}
