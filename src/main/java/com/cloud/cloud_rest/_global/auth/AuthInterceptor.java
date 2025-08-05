package com.cloud.cloud_rest._global.auth;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception401;
import com.cloud.cloud_rest._global.exception.Exception500;
import com.cloud.cloud_rest._global.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 요청이 컨트롤러 메소드가 맞는지 확인 (정적 리소스 등은 통과)
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 2. @Auth 어노테이션 확인
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

        // 3. @Auth가 없으면, 권한 검사 없이 통과
        if (auth == null) {
            return true;
        }

        // 4. @Auth가 있으면, JWT 검증 및 권한 검사 시작
        log.debug("@Auth 발견: JWT 및 권한 검사를 시작합니다.");
        String jwt = request.getHeader("Authorization");

        if (jwt == null || !jwt.startsWith("Bearer ")) {
            throw new Exception401("토큰이 없습니다. 로그인이 필요합니다.");
        }
        jwt = jwt.replace("Bearer ", "");

        try {
            SessionUser sessionUser = JwtUtil.verify(jwt);

            request.setAttribute("sessionUser", sessionUser);
            return true;

        } catch (TokenExpiredException e) {
            throw new Exception401("토큰이 만료되었습니다. 다시 로그인해주세요.");
        } catch (JWTDecodeException e) {
            throw new Exception401("유효하지 않은 토큰입니다.");
        } catch (Exception e) {
            log.error("인증 인터셉터 오류", e);
            throw new Exception500("서버 처리 중 오류가 발생했습니다.");
        }
    }
}
