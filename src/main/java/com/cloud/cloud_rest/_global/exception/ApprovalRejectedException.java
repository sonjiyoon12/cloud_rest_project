package com.cloud.cloud_rest._global.exception;

public class ApprovalRejectedException extends RuntimeException {
  private final String reason; // 거부 사유 필드

  public ApprovalRejectedException(String reason) {
    super("승인이 거부된 기업입니다"); // 공통 메시지
    this.reason = reason;
  }

  public String getReason() {
    return reason;
  }
}

