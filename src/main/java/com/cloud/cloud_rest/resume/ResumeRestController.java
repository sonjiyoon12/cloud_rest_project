package com.cloud.cloud_rest.resume;

import com.cloud.cloud_rest._global._core.common.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ResumeRestController {

    private final ResumeService resumeService;

    // 이력서 전체 조회
    @GetMapping("/resumes")
    public ResponseEntity<?> findAll() {
        List<ResumeResponse.ListDTO> resumes = resumeService.findAll();
        return ResponseEntity.ok().body(new ApiUtil<>(resumes));
    }

}
