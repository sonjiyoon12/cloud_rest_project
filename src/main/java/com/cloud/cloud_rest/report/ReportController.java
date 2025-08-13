package com.cloud.cloud_rest.report;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest.user.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/{id}")
    @Auth(roles = {Role.USER})
    public ResponseEntity<?> createReport(
            @Valid @RequestBody ReportRequestDto.Create requestDto,
            @PathVariable Long id,
            @RequestAttribute("sessionUser") SessionUser sessionUser) {
            ReportResponseDto responseDto =  reportService.createReport(requestDto,id,sessionUser);
            return ResponseEntity.ok(new ApiUtil<>(responseDto));
    }
}
