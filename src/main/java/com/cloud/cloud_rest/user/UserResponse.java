package com.cloud.cloud_rest.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.Base64;

public class UserResponse {


    // 유저 상세 정보 보여주기
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
        }
    }

    // 회원 가입 후 응답 DTO
    @Data
    public static class SaveDTO {
        private Long userId;
        private String username;
        private String email;
        private String userImage;
        private String createdAt;

        // 서버단에서 new UserResponse.JoinDTO(...)
        @Builder
        public SaveDTO(User user) {
            this.userId = user.getUserId();
            this.username = user.getUsername();
            this.email = user.getEmail();
            this.createdAt = user.getCreatedAt().toString();
        }
    }

    // 로그인 후 응답 DTO
    @Data
    public static class LoginDTO {
        private Long id;
        private String username;
        private String email;

        @Builder
        public LoginDTO(User user) {
            this.id = user.getUserId();
            this.username = user.getUsername();
            this.email = user.getEmail();
        }
    }

    // 회원 정보 수정후 응답 DTO
    @Data
    public static class UpdateDTO {
        private String username;
        private String phoneNumber;
        private String userImage;
        private String address;
        private String addressDefault;
        private String addressDetail;

        @Builder
        public UpdateDTO(User user) {
            this.username = user.getUsername();
            this.phoneNumber = user.getPhoneNumber();
            this.userImage = user.getUserImage();
            this.address = user.getAddress();
            this.addressDefault = user.getAddressDefault();
            this.addressDetail = user.getAddressDetail();
        }
    }


}
