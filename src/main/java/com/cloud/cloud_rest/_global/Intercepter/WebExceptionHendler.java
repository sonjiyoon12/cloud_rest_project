package com.cloud.cloud_rest._global.Intercepter;

import com.cloud.cloud_rest._global.utils.Define;
import com.cloud.cloud_rest._global.exception.*;
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
    public String ex401(Exception401 e, HttpServletRequest request) {
        log.warn(Define.SaveDTO.ERROR_401);
        request.setAttribute("msg", e.getMessage());
        return "err/401";
    }

    @ExceptionHandler(Exception403.class)
    public String ex403(Exception403 e, HttpServletRequest request) {
        log.warn(Define.SaveDTO.ERROR_403);
        request.setAttribute("msg", e.getMessage());
        return "err/403";
    }

    @ExceptionHandler(Exception404.class)
    public String ex404(Exception404 e, HttpServletRequest request) {
        log.warn(Define.SaveDTO.ERROR_404);
        request.setAttribute("msg", e.getMessage());
        return "err/404";
    }

    @ExceptionHandler(Exception500.class)
    public String ex500(Exception500 e, HttpServletRequest request) {
        log.warn(Define.SaveDTO.ERROR_500);
        request.setAttribute("msg", e.getMessage());
        return "err/500";
    }

    @ExceptionHandler(UserLoginExc.class)
    public String userLoginExc(UserLoginExc e, HttpServletRequest request) {
        log.warn(Define.SaveDTO.USER_LOGIN_ERROR);
        request.setAttribute("msg", e.getMessage());
        return "redirect:/user/login";
    }

    @ExceptionHandler(CompLoginExc.class)
    public String compLoginExc(CompLoginExc e, HttpServletRequest request) {
        log.warn(Define.SaveDTO.COMP_LOGIN_ERROR);
        request.setAttribute("msg", e.getMessage());
        return "redirect:/comp/login";
    }


}
