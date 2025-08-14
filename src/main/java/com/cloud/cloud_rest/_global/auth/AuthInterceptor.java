package com.cloud.cloud_rest._global.auth;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception401;
import com.cloud.cloud_rest._global.exception.Exception500;
import com.cloud.cloud_rest._global.utils.JwtUtil;
import com.cloud.cloud_rest.user.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 요청의 핸들러가 컨트롤러의 메소드인지 확인합니다.
        //    요청이 정적 리소스(예: HTML, CSS, JS)를 향하는 경우, handler는 HandlerMethod 타입이 아니므로, 인증 절차 없이 통과시킵니다.
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        // 2. 요청을 처리할 메소드에 @Auth 어노테이션이 붙어있는지 확인합니다.
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

        // 3. @Auth 어노테이션이 없다면, 별도의 인증/인가가 필요 없는 요청이므로 그대로 통과시킵니다.
        if (auth == null) {
            return true;
        }

        // 4. @Auth 어노테이션이 있다면, 인증 및 인가 절차를 시작합니다.
        log.debug("@Auth: JWT 및 권한 검사를 시작합니다.");

        // 5. HTTP 요청 헤더에서 "Authorization" 값을 가져옵니다.
        String jwt = request.getHeader("Authorization");

        // 6. 토큰이 없거나, "Bearer "로 시작하지 않으면 401 에러를 발생시킵니다.
        if (jwt == null || !jwt.startsWith("Bearer ")) {
            throw new Exception401("@Auth: 토큰이 없습니다. 로그인이 필요합니다.");
        }

        // 7. "Bearer " 접두사를 제거하여 순수한 JWT만 추출합니다.
        jwt = jwt.replace("Bearer ", "");

        try {
            // 8. JWT를 검증하고, 토큰이 유효하면 페이로드(사용자 정보)를 SessionUser 객체로 변환합니다.
            SessionUser sessionUser = JwtUtil.verify(jwt);

            // 9. 검증된 사용자 정보를 현재 요청의 속성(attribute)으로 저장하여, 컨트롤러에서 @RequestAttribute로 참조할 수 있게 합니다.
            request.setAttribute("sessionUser", sessionUser);

            // 10. @Auth 어노테이션에 지정된 역할(roles) 정보를 가져옵니다.
            Role[] allowedRoles = auth.roles();

            // 11. 허용된 역할이 지정되어 있다면, 현재 사용자의 역할과 비교하여 인가(Authorization) 처리를 수행합니다.
            if (allowedRoles.length > 0) {
                Role userRole = sessionUser.getRole();
                boolean authorized = Arrays.stream(allowedRoles)
                        .anyMatch(role -> role == userRole);

                // 12. 사용자의 역할이 허용된 역할 목록에 없다면, 401 에러를 발생시킵니다.
                if (!authorized) {
                    throw new Exception401("@Auth: 해당 권한이 없습니다.");
                }
            }

            // 13. 모든 검증을 통과하면, 요청을 컨트롤러로 전달합니다.
            return true;

        } catch (TokenExpiredException e) {
            // JWT 유효기간이 만료된 경우 401 에러를 발생시킵니다.
            throw new Exception401("@Auth: 토큰이 만료되었습니다. 다시 로그인해주세요.");
        } catch (JWTDecodeException e) {
            // JWT 형식이 잘못되었거나, 서명이 유효하지 않은 경우 401 에러를 발생시킵니다.
            throw new Exception401("@Auth: 유효하지 않은 토큰입니다.");
        } catch (Exception e) {
            // 그 외 예측하지 못한 예외가 발생한 경우, 로그를 남기고 500 서버 에러를 발생시킵니다.
            log.error("@Auth: 인증 인터셉터 오류", e);
            throw new Exception500("@Auth: 서버 처리 중 오류가 발생했습니다.");
        }
    }
}
