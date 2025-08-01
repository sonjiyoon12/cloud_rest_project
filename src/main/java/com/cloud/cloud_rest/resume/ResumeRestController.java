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

    // 게시글 목록 조회
    @GetMapping("/")
    public ResponseEntity<ApiUtil<List<ResumeResponse.ListDTO>>> List(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "5") int size) {

        List<ResumeResponse.ListDTO> resumeList = resumeService.list(page, size);
        return ResponseEntity.ok(new ApiUtil<>(resumeList));
    }

}
