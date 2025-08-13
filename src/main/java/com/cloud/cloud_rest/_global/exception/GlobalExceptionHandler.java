package com.cloud.cloud_rest._global.exception;

import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.utils.Define;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid 어노테이션 유효성 검사 실패 시 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        log.warn("유효성 검사 실패: {}", errors);
        // ApiUtil의 새로운 생성자를 사용하여, 상세 에러 내용을 body에 담아 반환
        return new ResponseEntity<>(new ApiUtil<>(400, "유효성 검사 실패", errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<ApiUtil<?>> ex400(Exception400 e) {
        log.warn(Define.SaveDTO.ERROR_400);
        return new ResponseEntity<>(new ApiUtil<>(400, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<ApiUtil<?>> ex401(Exception401 e) {
        log.warn(Define.SaveDTO.ERROR_401);
        return new ResponseEntity<>(new ApiUtil<>(401, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<ApiUtil<?>> ex403(Exception403 e) {
        log.warn(Define.SaveDTO.ERROR_403);
        return new ResponseEntity<>(new ApiUtil<>(403, e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<ApiUtil<?>> ex404(Exception404 e) {
        log.warn(Define.SaveDTO.ERROR_404);
        return new ResponseEntity<>(new ApiUtil<>(404, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<ApiUtil<?>> ex500(Exception500 e) {
        log.warn(Define.SaveDTO.ERROR_500);
        return new ResponseEntity<>(new ApiUtil<>(500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserLoginExc.class)
    public ResponseEntity<ApiUtil<?>> userLoginExc(UserLoginExc e) {
        log.warn(Define.SaveDTO.USER_LOGIN_ERROR);
        return new ResponseEntity<>(new ApiUtil<>(401, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CompLoginExc.class)
    public ResponseEntity<ApiUtil<?>> compLoginExc(CompLoginExc e) {
        log.warn(Define.SaveDTO.COMP_LOGIN_ERROR);
        return new ResponseEntity<>(new ApiUtil<>(401, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ApprovalRejectedException.class)
    public ResponseEntity<ApiUtil<?>> handleApprovalRejected(ApprovalRejectedException e) {
        log.warn("승인 거부된 기업 로그인 시도: {}", e.getReason());
        return new ResponseEntity<>(
                new ApiUtil<>(403, e.getMessage() + " 사유: " + e.getReason()),
                HttpStatus.FORBIDDEN
        );
    }
}
