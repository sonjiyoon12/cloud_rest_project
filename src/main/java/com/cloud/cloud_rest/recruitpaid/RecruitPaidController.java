package com.cloud.cloud_rest.recruitpaid;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/paids")
public class RecruitPaidController {

    private final RecruitPaidService recruitPaidService;

    //유료 공고 전환
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
}
