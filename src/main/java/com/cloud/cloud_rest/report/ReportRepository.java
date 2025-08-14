package com.cloud.cloud_rest.report;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {

    // ReportStatus 값을 기준으로 신고 목록을 조회하는 메서드
    List<Report> findByStatus(ReportStatus status);
}
