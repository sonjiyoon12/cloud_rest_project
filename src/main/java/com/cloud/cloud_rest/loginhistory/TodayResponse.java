package com.cloud.cloud_rest.loginhistory;

import com.cloud.cloud_rest.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public class TodayResponse {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TodayLoginResponse {
        private int totalCount;
        private List<LoginHistoryResponse.LoginDTO> users;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TodaySignResponse {
        private int totalCount;
        private List<UserResponse.SaveDTO> users;

    }
}
