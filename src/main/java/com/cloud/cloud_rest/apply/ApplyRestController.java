package com.cloud.cloud_rest.apply;

import com.cloud.cloud_rest._core.common.ApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApplyRestController {

    private final ApplyService applyService;

    // 공고 지원
    @PostMapping("/applies")
    public ResponseEntity<?> save() {
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(""));
    }

    // 전체 공고 지원 내역 조회
    @GetMapping("/applies")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(new ApiUtil<>(""));
    }

    // 특정 공고 지원 내역 조회
    @GetMapping("/applies/{id}")
    public ResponseEntity<?> findById() {
        return ResponseEntity.ok().body(new ApiUtil<>(""));
    }

    // 특정 공고 지원 내역 삭제
    @DeleteMapping("/applies/{id}")
    public ResponseEntity<?> deleteById() {
        return ResponseEntity.ok().body(new ApiUtil<>("삭제 성공"));
    }
}
