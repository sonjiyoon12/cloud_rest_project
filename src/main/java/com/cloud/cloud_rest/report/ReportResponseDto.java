package com.cloud.cloud_rest.report;


import com.cloud.cloud_rest.board.Board;
import com.cloud.cloud_rest.user.User;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponseDto {

    private Long id;
    private ReportType reportType;
    private Board board;
    private String reason;
    private User reportId;
    private ReportStatus status;
    private LocalDateTime createdAt;

    @Builder
    public ReportResponseDto (Report report) {
        this.id = report.getId();
        this.reportType = report.getReportType();
        this.board = report.getBoard();
        this.reason = report.getReason();
        this.reportId = report.getReporter();
        this.status = report.getStatus();
        this.createdAt = report.getCreatedAt();
    }
}
