package com.cloud.cloud_rest.recruit_paid;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest.user.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/paids")
public class RecruitPaidController {

    private final RecruitPaidService recruitPaidService;

    //유료 공고 전환
    @Auth(roles = {Role.CORP, Role.ADMIN})
    @PostMapping
    public ResponseEntity<RecruitPaidResponse.PaidSaveDTO> paidSave(
            @RequestBody @Valid RecruitPaidRequest.PaidSaveDTO dto,
            @RequestAttribute SessionUser sessionUser) {
        RecruitPaidResponse.PaidSaveDTO responseDTO = recruitPaidService.paidSave(dto, sessionUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    //유료공고 조회
    @GetMapping("/{recruitId}")
    public ResponseEntity<?> paidDetail(@PathVariable Long recruitId) {
        RecruitPaidResponse.PaidDetailDTO dto = recruitPaidService.paidDetail(recruitId);
        return ResponseEntity.ok(new ApiUtil<>(dto));
    }

    // [관리자] 전체 유료 공고 목록 조회
    @Auth(roles = {Role.ADMIN})
    @GetMapping("/admin")
    public ResponseEntity<?> paidList() {
        List<RecruitPaidResponse.PaidListDTO> responseDTOs = recruitPaidService.paidList();
        return ResponseEntity.ok(new ApiUtil<>(responseDTOs));
    }

    // [관리자] 유료 공고 강제 삭제
    @Auth(roles = {Role.ADMIN})
    @DeleteMapping("/admin/{recruitId}")
    public ResponseEntity<?> paidDelete(@PathVariable Long recruitId) {
        recruitPaidService.paidDelete(recruitId);
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}
