package com.cloud.cloud_rest.user;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

public class UserRequest {

    @Data
    public static class SaveDTO{
        
        @NotBlank(message = "이름을 입력해 주시기 바랍니다")
        private String username;
        @NotBlank(message = "아이디를 입력해 주시기 바랍니다")
        private String loginId;
        @NotBlank(message = "비밀번호를 입력해 주시기 바랍니다")
        private String password;
        @NotBlank(message = "이메일을 입력해 주시기 바랍니다")
        private String email;
        @NotBlank(message = "전화번호를 입력해 주시기 바랍니다")
        private String phoneNumber;
        @NotBlank(message = "성별을 입력해 주시기 바랍니다")
        private String sex;

        @NotNull(message = "나이를 입력해주세요") // Integer일 경우
        @Min(value = 1, message = "나이는 1살 이상이어야 합니다")
        private Integer age;

        @NotBlank(message = "지번을 입력해주세요")
        private String address; // 지번

        @NotBlank(message = "기본주소 입력해주세요")
        private String addressDefault; // 기본 주소

        @NotBlank(message = "상세주소를 입력해주세요")
        private String addressDetail; // 상세 주소

        @NotBlank(message = "비밀번호를 재 입력해주세요")
        private String rePassword; // 비밀번호 재 입력

        public User toEntity(String encodePassword){
            return User.builder()
                    .username(this.username)
                    .loginId(this.loginId)
                    .password(encodePassword)
                    .email(this.email)
                    .phoneNumber(this.phoneNumber)
                    .sex(this.sex)
                    .age(this.age)
                    .address(this.address)
                    .addressDefault(this.addressDefault)
                    .addressDetail(this.addressDetail)
                    .build();
        }
    }

    @Data
    public static class LoginDTO{
        @NotBlank(message = "아이디를 입력해 주시기 바랍니다")
        private String loginId;
        @NotBlank(message = "비밀번호를 입력해 주시기 바랍니다")
        private String password;
    }

    @Data
    public static class UpdateDTO{
        private String username;
        private String phoneNumber;
        private MultipartFile userImage;
        private String address;
        private String addressDefault;
        private String addressDetail;
    }


}
