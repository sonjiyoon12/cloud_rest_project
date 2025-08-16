package com.cloud.cloud_rest.loginhistory;

import lombok.Builder;
import lombok.Data;

public class LoginHistoryResponse {

    @Data
    public static class LoginDTO{
        private Long userId;
        private String username;
        private String loginId;
        private String phoneNumber;
        private String sex;
        private int age;
        private String address;
        private String addressDefault;
        private String addressDetail;
        private String ipAddress;
        private String userAgent;
        private String loginTime;

        @Builder
        public LoginDTO(LoginHistory loginHistory) {
            this.userId = loginHistory.getUser().getUserId();
            this.username = loginHistory.getUser().getUsername();
            this.loginId = loginHistory.getUser().getLoginId();
            this.phoneNumber = loginHistory.getUser().getPhoneNumber();
            this.sex = loginHistory.getUser().getSex();
            this.age = loginHistory.getUser().getAge();
            this.address = loginHistory.getUser().getAddress();
            this.addressDefault = loginHistory.getUser().getAddressDefault();
            this.addressDetail = loginHistory.getUser().getAddressDetail();
            this.ipAddress = loginHistory.getIpAddress();
            this.userAgent = loginHistory.getUserAgent();
            this.loginTime = loginHistory.getTime();
        }
    }
}
