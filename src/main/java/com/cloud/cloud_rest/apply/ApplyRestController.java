package com.cloud.cloud_rest.apply;

import com.cloud.cloud_rest._global._core.common.ApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ApplyRestController {

    private final ApplyService applyService;

    // 공고 지원
    @PostMapping("/applies")
    public ResponseEntity<?> save(@RequestParam(name = "resumeId") Long resumeId,
                                  @RequestParam(name = "recruitId") Long recruitId) {

        ApplyResponse.SaveDTO savedApply = applyService.save(resumeId, recruitId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(savedApply));
    }

    // 전체 공고 지원 내역 조회
    @GetMapping("/applies")
    public ResponseEntity<?> findAll() {
        List<ApplyResponse.DetailDTO> applies = applyService.findAll();
        return ResponseEntity.ok().body(new ApiUtil<>(applies));
    }

    // 특정 공고 지원 내역 조회
    @GetMapping("/applies/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "applyId") Long applyId) {
        ApplyResponse.DetailDTO apply = applyService.findById(applyId);
        return ResponseEntity.ok().body(new ApiUtil<>(apply));
    }

    // 특정 공고 지원 내역 삭제
    @DeleteMapping("/applies/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "applyId") Long applyId) {
        applyService.deleteById(applyId);
        return ResponseEntity.ok().body(new ApiUtil<>("삭제 성공"));
    }
}
