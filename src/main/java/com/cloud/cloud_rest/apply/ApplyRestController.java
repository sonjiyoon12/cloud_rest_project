package com.cloud.cloud_rest.apply;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/applies")
@Tag(name = "Apply", description = "공고 지원 관리 API")
public class ApplyRestController {

    private final ApplyService applyService;

    // 공고 지원
    @Operation(summary = "공고 지원")
    @PostMapping
    public ResponseEntity<?> save(@RequestBody ApplyRequest.SaveDTO saveDTO) {

        ApplyResponse.SaveDTO savedApply = applyService.save(saveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(savedApply));
    }

    // 전체 공고 지원 내역 조회
    @Operation(summary = "전체 공고 지원 내역 조회")
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<ApplyResponse.DetailDTO> applies = applyService.findAll();
        return ResponseEntity.ok().body(new ApiUtil<>(applies));
    }

    // 특정 공고 지원 내역 조회
    @Operation(summary = "특정 공고 지원 내역 조회")
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(name = "id") Long applyId,
                                      @RequestAttribute("sessionUser")SessionUser sessionUser) {
        ApplyResponse.DetailDTO apply = applyService.findById(applyId, sessionUser);
        return ResponseEntity.ok().body(new ApiUtil<>(apply));
    }

    // 특정 공고 지원 내역 삭제
    @Operation(summary = "특정 공고 지원 내역 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long applyId) {
        applyService.deleteById(applyId);
        return ResponseEntity.ok().body(new ApiUtil<>("삭제 성공"));
    }
}
