package com.cloud.cloud_rest._global.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.User;

import java.util.Date;

/**
 * JWT 토큰 생성 및 검증을 담당하는 유틸 클래스
 *
 * JWT 구조 :
 * - Header : 토큰 타입과 암호화 알고리즘 정보
 * - Payload : 사용자 정보와 토큰 메타데이터
 * - Signature : 토큰의 무결성을 보장하는 서명
 */
public class JwtUtil {

    // JWT 서명에 사용할 비밀키 선언(실세 운영에서는 환경변수로 관리)
    private static final String SECRET_KEY = "cloud_rest";
    // 토큰 만료 시간(1시간 = 1000 * 60 * 60)
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;
    // 토큰 주세 (이 애플리케이션을 식별하는 값)
    private static final String SUBJECT = "Cloud_rest_blog";

    public static String createForUser(User user) {
        return createToken(user.getUserId(), user.getUsername(), user.getEmail(), "USER");
    }

    public static String createForCorp(Corp corp) {
        return createToken(corp.getCorpId(), corp.getLoginId(), corp.getEmail(), "CORP");
    }

    /**
     * JWT 토큰을 생성하는 메서드
     */
    private static String createToken(Long id, String username, String email, String role) {
        Date expiresAt = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        return JWT.create()
                .withSubject(SUBJECT)
                .withExpiresAt(expiresAt)
                .withClaim("id", id)
                .withClaim("username", username)
                .withClaim("email", email)
                .withClaim("role", role)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC512(SECRET_KEY));
    }

    /**
     * JWT 토큰 검증 및 사용자 정보 추출 메서드
     */

    public static SessionUser verify(String jwt) {

        // JWT 디코딩
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .withSubject(SUBJECT)
                .build().verify(jwt);

        // 풀린 토큰 정보에서 사용자 정보 추출 (민감한 정보는 넣지 않는다)
        Long id = decodedJWT.getClaim("id").asLong();
        String userId = decodedJWT.getClaim("userId").asString();
        String username = decodedJWT.getClaim("username").asString();
        String role = decodedJWT.getClaim("role").asString();

        return SessionUser.builder()
                .id(id)
                .userId(userId)
                .username(username)
                .role(role)
                .build();
    }

    // JWT 토큰에서 사용자 ID만 추출하는 편의 메서드
    public static Long getUserId(String jwt) {
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET_KEY))
                .withSubject(SUBJECT)
                .build()
                .verify(jwt);
        return  decodedJWT.getClaim("id").asLong();
    }

    // JWT 토큰의 유성만 검사하는 메서드
    public static boolean isValid(String jwt) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SECRET_KEY))
                    .withSubject(SUBJECT)
                    .build()
                    .verify(jwt);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}





