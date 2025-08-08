package com.cloud.cloud_rest._global.utils;

import jakarta.servlet.http.HttpServletRequest;

public class HttpRequestUtil {
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        System.out.println("User-Agent: " + request.getHeader("User-Agent"));
        return userAgent != null ? userAgent : "UNKNOWN";
    }
}
