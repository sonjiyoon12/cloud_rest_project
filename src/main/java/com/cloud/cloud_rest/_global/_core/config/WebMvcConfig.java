package com.cloud.cloud_rest._global._core.config;

import com.cloud.cloud_rest._global._core.interceptor.LoginInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration // IoC 처리 (싱글톤 패턴 관리)
public class WebMvcConfig implements WebMvcConfigurer {
    // DI 처리(생성자 의존 주입)
    private final LoginInterceptor loginInterceptor;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                // REST API 경로 변경
                .addPathPatterns("/api/**")
                // 공개 API는 제외 처리
                .excludePathPatterns(
                        // 게시판
                        "/api/boards/{id:\\d+}/detail",
                        // 회원가입
                        "/api/corp/save",
                        "/api/users/save",
                        // 로그인
                        "/api/corp/login",
                        "/api/users/login",
                        // 구인공고
                        "/api/recruits",
                        "/api/recruits/{id:\\d+}",
                        // 이력서
                        "/api/resumes",
                        // 에러
                        "/api/**/err/**"
                );

    }

    // cors 정책 설정

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api-test/**")
                //.allowedOrigins("https://api.kakao.com:8080") 특정 도메인만 등록 가능
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(false); // 인증이 필요한 경우 true

        // 필요하다면 중복 등록 가능
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);

    }


}
