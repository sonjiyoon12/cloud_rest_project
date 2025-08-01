package com.cloud.cloud_.rest._define;

import lombok.Data;

public class Define {

    @Data
    public static class SaveDTO{

        public static final String ERROR_400 = "잘못된 요청입니다_400";
        public static final String ERROR_401 = "요청이 유효하지 않습니다_401";
        public static final String ERROR_403 = "권한이 없습니다_403";
        public static final String ERROR_404 = "존재하지 않습니다_404";
        public static final String ERROR_500 = "서버오류_500";
        public static final String USER_LOGIN_ERROR = "유저 로그인 오류";
        public static final String COMPANY_LOGIN_ERROR = "회사 로그인 오류";

    }

}
