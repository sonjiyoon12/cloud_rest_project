package com.cloud.cloud_rest._global.utils;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest.user.Role;

public class AuthorizationUtil {
    public static void validateUserAccess(Long requestedUserId, SessionUser sessionUser) {
        if (!requestedUserId.equals(sessionUser.getId()) && sessionUser.getRole() != Role.ADMIN) {
            throw new Exception403("본인 정보만 조회 가능합니다.");
        }
    }

    public static void validateCorpAccess(Long requestedCorpId, SessionUser sessionUser) {
        if (!requestedCorpId.equals(sessionUser.getId()) && sessionUser.getRole() != Role.ADMIN) {
            throw new Exception403("본인 정보만 조회 가능합니다.");
        }
    }

    public static void validateAdminAccess(SessionUser sessionUser){
        if(sessionUser.getRole() != Role.ADMIN){
            throw new Exception403("관리자 유저만 접근 가능합니다.");
        }
    }
}
