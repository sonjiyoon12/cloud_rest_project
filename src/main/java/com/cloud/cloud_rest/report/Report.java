package com.cloud.cloud_rest.report;

import com.cloud.cloud_rest.board.Board;
import com.cloud.cloud_rest.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 신고 유형 : 게시물, 댓글
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType reportType;

    // 신고 대상 Id ( 게시물 Id, 댓글 Id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    // 신고 사유
    @Column(nullable = false)
    private String reason;

    // 신고한 사용자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private User reporter;

    // 신고 처리 상태 : 대기, 처리 중, 완료
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportStatus status = ReportStatus.PENDING;

    // 신고 생성 일시
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 생성자
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
