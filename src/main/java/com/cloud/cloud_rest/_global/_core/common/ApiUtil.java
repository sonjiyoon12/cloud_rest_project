package com.cloud.cloud_rest._global._core.common;

import lombok.Data;

@Data
public class ApiUtil<T> {
    private Integer status;
    private String msg;
    private T body;

    // 성공 응답 생성자
    public ApiUtil(T body) {
        this.status = 200;
        this.msg = "성공";
        this.body = body;
    }

    // 실패 응답 생성자 (기존)
    public ApiUtil(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
        this.body = null;
    }

    // 실패 응답 생성자 v2 (상세 내용 포함)
    public ApiUtil(Integer status, String msg, T body) {
        this.status = status;
        this.msg = msg;
        this.body = body;
    }
}
