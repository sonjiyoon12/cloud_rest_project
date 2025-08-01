package com.cloud.cloud_rest.errors.interceptor;

import com.cloud.cloud_rest._define.Define;
import com.cloud.cloud_rest.errors.exception.*;
import com.cloud.cloud_rest.errors.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class WebExceptionHendler {

    @ExceptionHandler(Exception400.class)
    public String ex400(Exception400 e, HttpServletRequest request) {
        log.warn(Define.SaveDTO.ERROR_400);
        request.setAttribute("msg", e.getMessage());
        return "err/400";
    }

    @ExceptionHandler(Exception401.class)
    public String ex400(Exception401 e, HttpServletRequest request) {
        log.warn(Define.SaveDTO.ERROR_401);
        request.setAttribute("msg", e.getMessage());
        return "err/401";
    }

    @ExceptionHandler(Exception403.class)
    public String ex400(Exception403 e, HttpServletRequest request) {
        log.warn(Define.SaveDTO.ERROR_403);
        request.setAttribute("msg", e.getMessage());
        return "err/403";
    }

    @ExceptionHandler(Exception404.class)
    public String ex400(Exception404 e, HttpServletRequest request) {
        log.warn(Define.SaveDTO.ERROR_404);
        request.setAttribute("msg", e.getMessage());
        return "err/404";
    }

    @ExceptionHandler(Exception500.class)
    public String ex400(Exception500 e, HttpServletRequest request) {
        log.warn(Define.SaveDTO.ERROR_500);
        request.setAttribute("msg", e.getMessage());
        return "err/500";
    }

}
