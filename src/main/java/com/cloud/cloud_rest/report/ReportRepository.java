package com.cloud.cloud_rest.report;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    // ReportStatus 값을 기준으로 신고 목록을 조회하는 메서드
    List<Report> findByStatus(ReportStatus status);

    @Query("SELECT DISTINCT r FROM Report r " +
            "JOIN FETCH r.board b " +
            "LEFT JOIN FETCH b.comments c " +
            "LEFT JOIN FETCH c.user u")
    List<Report> findAllWithDetails();

    @Query("SELECT r FROM Report r " +
            "JOIN FETCH r.board b " +
            "LEFT JOIN FETCH b.comments c " +
            "LEFT JOIN FETCH c.user u " +
            "WHERE r.id = :id")
    Optional<Report> findByIdWithDetails(@Param("id") Long id);
}
