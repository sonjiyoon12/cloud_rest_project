package com.cloud.cloud_rest.recruit_paid;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest.user.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "RecruitPaid : 관리자용 유료공고 API", description = "관리자가 유료공고를 조회하고 관리하게 해주는 API입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/paids")
public class RecruitPaidController {

    private final RecruitPaidService recruitPaidService;

    @Operation(summary = "[관리자/기업] 유료공고 상세 조회", description = "특정 공고의 유료 상태(남은 기간 등)를 조회합니다.", security = @SecurityRequirement(name = "jwt"))
    @GetMapping("/{recruitId}")
    @Auth(roles = {Role.CORP, Role.ADMIN})
    public ResponseEntity<?> paidDetail(@PathVariable Long recruitId, @RequestAttribute SessionUser sessionUser) {
        RecruitPaidResponse.PaidDetailDTO dto = recruitPaidService.paidDetail(recruitId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(dto));
    }

    @Operation(summary = "[관리자] 전체 유료 공고 목록 조회", description = "관리자가 모든 유료 공고 목록을 조회합니다.", security = @SecurityRequirement(name = "jwt"))
    @Auth(roles = {Role.ADMIN})
    @GetMapping("/admin")
    public ResponseEntity<?> paidList() {
        List<RecruitPaidResponse.PaidListDTO> responseDTOs = recruitPaidService.paidList();
        return ResponseEntity.ok(new ApiUtil<>(responseDTOs));
    }

    @Operation(summary = "[관리자] 유료 공고 강제 삭제", description = "관리자가 특정 유료 공고를 강제로 삭제합니다.", security = @SecurityRequirement(name = "jwt"))
    @Auth(roles = {Role.ADMIN})
    @DeleteMapping("/admin/{recruitId}")
    public ResponseEntity<?> paidDelete(@PathVariable Long recruitId) {
        recruitPaidService.paidDelete(recruitId);
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}
