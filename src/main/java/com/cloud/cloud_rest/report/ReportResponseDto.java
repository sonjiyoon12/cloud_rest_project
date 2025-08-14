package com.cloud.cloud_rest.report;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponseDto {

    private Long id;
    private ReportType reportType;
    private Long boardId;
    private String reason;
    private Long reporterId;
    private ReportStatus status;
    private LocalDateTime createdAt;

    @Builder
    public ReportResponseDto (Report report) {
        this.id = report.getId();
        this.reportType = report.getReportType();
        this.boardId = report.getBoard().getBoardId();
        this.reason = report.getReason();
        this.reporterId = report.getReporter().getUserId();
        this.status = report.getStatus();
        this.createdAt = report.getCreatedAt();
    }
}