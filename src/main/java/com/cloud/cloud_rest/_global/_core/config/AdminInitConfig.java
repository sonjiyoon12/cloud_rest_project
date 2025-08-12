package com.cloud.cloud_rest._global._core.config;

import com.cloud.cloud_rest.user.Role;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@Profile("dev") // 'dev' 프로파일에서만 활성화
@Configuration
public class AdminInitConfig {

    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUserId("admin").isEmpty()) {
                User admin = User.builder()
                        .loginId("admin")
                        .password(passwordEncoder.encode("admin")) // 비밀번호는 'admin'로 암호화하여 설정
                        .email("admin")
                        .username("admin")
                        .role(Role.ADMIN)
                        .build();
                userRepository.save(admin);
                System.out.println("초기 관리자 계정 'admin'이 생성되었습니다.");
            }
        };
    }
}
