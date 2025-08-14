package com.cloud.cloud_rest.admin;

import com.cloud.cloud_rest.board.Board;
import com.cloud.cloud_rest.report.Report;
import com.cloud.cloud_rest.report.ReportStatus;
import com.cloud.cloud_rest.report.ReportType;
import com.cloud.cloud_rest.user.Role;
import com.cloud.cloud_rest.user.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class AdminResponse {
    @Data
    public static class SaveDTO {
        private Long userId;
        private String username;
        private String email;
        private String userImage;
        private Role role;
        private String createdAt;

        @Builder
        public SaveDTO(User user) {
            this.userId = user.getUserId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.role = user.getRole();
            this.createdAt = user.getCreatedAt().toString();
        }
    }

    // 로그인 후 응답 DTO
    @Data
    public static class LoginDTO {
        private Long id;
        private String username;
        private String email;
        private Role role;

        @Builder
        public LoginDTO(User user) {
            this.id = user.getUserId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.role = user.getRole();
        }
    }

    @Data
    public static class UserDTO {
        private Long userId;
        private String username;
        private String loginId;
        private String email;
        private String phoneNumber;
        private String sex;
        private int age;
        private String userImage;
        private String address;
        private String addressDefault;
        private String addressDetail;
        private String createdAt;
        private Role role;

        @Builder
        public UserDTO(User user) {
            this.userId = user.getUserId();
            this.username = user.getUsername();
            this.loginId = user.getLoginId();
            this.email = user.getEmail();
            this.phoneNumber = user.getPhoneNumber();
            this.sex = user.getSex();
            this.age = user.getAge();
            this.userImage = user.getUserImage();
            this.address = user.getAddress();
            this.addressDefault = user.getAddressDefault();
            this.addressDetail = user.getAddressDetail();
            this.createdAt = user.getCreatedAt().toString();
            this.role = user.getRole();
        }
    }

    // 관리자 페이지에서 신고 목록을 보여주기 위한 DTO
    @Data
    public static class ReportListDTO {
        private Long id;
        private ReportType reportType;
        private Board targetId;
        private String reason;
        private ReportStatus status;
        private LocalDateTime createdAt;
        private Long reporterId; // 신고한 유저 ID

        @Builder
        public ReportListDTO(Report report) {
            this.id = report.getId();
            this.reportType = report.getReportType();
            this.targetId = report.getBoard();
            this.reason = report.getReason();
            this.status = report.getStatus();
            this.createdAt = report.getCreatedAt();
            this.reporterId = report.getReporter().getUserId();
        }

        // Report 엔티티 리스트를 ReportListDTO 리스트로 변환하는 정적 메서드
        public static List<ReportListDTO> fromEntityList(List<Report> reports) {
            return reports.stream()
                    .map(ReportListDTO::new)
                    .collect(Collectors.toList());
        }
    }

    // 신고 상태를 업데이트하기 위한 Request DTO (Controller 에서 RequestBody로 받음)
    @Data
    public static class ReportStatusUpdateDTO {
        private ReportStatus status;
    }
}
