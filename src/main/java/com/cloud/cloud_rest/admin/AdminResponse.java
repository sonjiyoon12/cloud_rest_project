package com.cloud.cloud_rest.admin;

import com.cloud.cloud_rest.user.Role;
import com.cloud.cloud_rest.user.User;
import lombok.Builder;
import lombok.Data;

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
}
