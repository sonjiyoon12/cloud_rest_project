package com.cloud.cloud_rest.report;

public enum ReportStatus {
    PENDING, // 신고 접수 후 처리 대기
    IN_PROGRESS,  // 관리자가 처리 중
    COMPLETED // 신고 처리 완료
}
