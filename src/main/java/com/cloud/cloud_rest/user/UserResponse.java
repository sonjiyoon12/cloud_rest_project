package com.cloud.cloud_rest.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

public class UserResponse {

    @Data
    @AllArgsConstructor
    public static class UserDTO{
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
        private Timestamp createdAt;

        public static UserDTO fromEntity(User user){
            return new UserDTO(
                    user.getUserId(),
                    user.getUsername(),
                    user.getLoginId(),
                    user.getEmail(),
                    user.getPhoneNumber(),
                    user.getSex(),
                    user.getAge(),
                    user.getUserImage(),
                    user.getAddress(),
                    user.getAddressDefault(),
                    user.getAddressDetail(),
                    user.getCreatedAt()
            );
        }
    }


}
