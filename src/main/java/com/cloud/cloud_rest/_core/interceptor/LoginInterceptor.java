package com.tenco.blog._core.interceptor;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.tenco.blog._core.errors.exception.Exception401;
import com.tenco.blog._core.errors.exception.Exception500;
import com.tenco.blog._core.utils.Define;
import com.tenco.blog._core.utils.JwtUtil;
import com.tenco.blog.user.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component // Ioc 대상 (싱글톤 패턴으로 관리)
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * preHandle - 컨트롤러에 들어 가지 전에 동작 하는 메서드이다.
     * 리턴타입이 boolean 이라서 true ---> 컨트롤러 안으로 들어간다, false --> 못 들어 감
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

      log.debug("==== JWT 인증 인터셉터 시작 ====");
      String jwt = request.getHeader("Authorization");
      // Bearer + 공백sdkjhkjhasdfkj.sadlkjfasld.asdjfhalsf
      if(jwt == null || jwt.startsWith("Bearer ") == false) {
        throw new Exception401("JWT 토큰을 전달해주세요");
      }
      jwt = jwt.replace("Bearer ", "");

      try {
           SessionUser sessionUser = JwtUtil.verify(jwt);

          // 구분 중요!!! (임시 세션 사용)
          // HttpSession session = request.getSession(); // JSSIONID="asdas1231234"
          // session.setAttribute(Define.SESSION_USER, sessionUser);

          // JWT 는 stateless 개념을 지키기 위해서 나옴 (모바일 쿠기에 접근 못함)
          // request.setAttribute는 요청 단위로 데이터를 저장하고 소멸 합니다.
          // 즉, 해당 데이터는 요청이 처리된 후 사리지며, 서버에 세션메로리에 저장되지 않습니다.
          request.setAttribute(Define.SESSION_USER, sessionUser);
          return true;

      } catch (TokenExpiredException e) {
          throw new Exception401("토큰 만료시간이 지났어요. 다시 로그인하세요");
      } catch (JWTDecodeException e) {
          throw new Exception401("토큰이 유효하지 않습니다");
      } catch (Exception e) {
          throw new Exception500(e.getMessage());
      }
    }

    // 뷰가 렌터링 되기전에 콜백 되는 메서드
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    // 뷰가 완전 렌더링이 된 후 호출 될 수 있다.
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
