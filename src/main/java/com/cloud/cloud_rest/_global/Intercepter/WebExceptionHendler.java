package com.cloud.cloud_rest._global.Intercepter;

import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.utils.Define;
import com.cloud.cloud_rest._global.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice // [수정] REST API용 예외 처리를 위해 @RestControllerAdvice로 변경
public class WebExceptionHendler {

    @ExceptionHandler(Exception400.class)
    public ResponseEntity<ApiUtil<?>> ex400(Exception400 e) {
        log.warn(Define.SaveDTO.ERROR_400);
        // [수정] ApiUtil의 실패 생성자를 호출하고, 정확한 HTTP 상태 코드(400)를 반환
        return new ResponseEntity<>(new ApiUtil<>(400, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception401.class)
    public ResponseEntity<ApiUtil<?>> ex401(Exception401 e) {
        log.warn(Define.SaveDTO.ERROR_401);
        // [수정] ApiUtil을 사용해 JSON 형식으로 응답하고, 정확한 HTTP 상태 코드(401)를 반환
        return new ResponseEntity<>(new ApiUtil<>(401, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception403.class)
    public ResponseEntity<ApiUtil<?>> ex403(Exception403 e) {
        log.warn(Define.SaveDTO.ERROR_403);
        // [수정] ApiUtil을 사용해 JSON 형식으로 응답하고, 정확한 HTTP 상태 코드(403)를 반환
        return new ResponseEntity<>(new ApiUtil<>(403, e.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception404.class)
    public ResponseEntity<ApiUtil<?>> ex404(Exception404 e) {
        log.warn(Define.SaveDTO.ERROR_404);
        // [수정] ApiUtil을 사용해 JSON 형식으로 응답하고, 정확한 HTTP 상태 코드(404)를 반환
        return new ResponseEntity<>(new ApiUtil<>(404, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception500.class)
    public ResponseEntity<ApiUtil<?>> ex500(Exception500 e) {
        log.warn(Define.SaveDTO.ERROR_500);
        // [수정] ApiUtil을 사용해 JSON 형식으로 응답하고, 정확한 HTTP 상태 코드(500)를 반환
        return new ResponseEntity<>(new ApiUtil<>(500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserLoginExc.class)
    public ResponseEntity<ApiUtil<?>> userLoginExc(UserLoginExc e) {
        log.warn(Define.SaveDTO.USER_LOGIN_ERROR);
        // [수정] 로그인 실패는 401 Unauthorized가 적절합니다.
        return new ResponseEntity<>(new ApiUtil<>(401, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(CompLoginExc.class)
    public ResponseEntity<ApiUtil<?>> compLoginExc(CompLoginExc e) {
        log.warn(Define.SaveDTO.COMP_LOGIN_ERROR);
        // [수정] 로그인 실패는 401 Unauthorized가 적절합니다.
        return new ResponseEntity<>(new ApiUtil<>(401, e.getMessage()), HttpStatus.UNAUTHORIZED);
    }


}
