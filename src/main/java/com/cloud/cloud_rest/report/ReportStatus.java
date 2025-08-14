package com.cloud.cloud_rest.report;


public enum ReportStatus {
    PENDING, // 신고 접수 후 처리 대기
    COMPLETED, // 신고 처리 완료
    REJECTED, // 요청 거부
}