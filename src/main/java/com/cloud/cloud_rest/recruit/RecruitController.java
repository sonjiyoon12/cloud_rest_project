package com.cloud.cloud_rest.recruit;

import com.cloud.cloud_rest._global._core.common.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruits")
public class RecruitController {

    private final RecruitService recruitService;

    // 공고 저장
    @PostMapping
    public ResponseEntity<ApiUtil<RecruitResponse.RecruitListDTO>> save(@RequestBody RecruitRequest.SaveDTO dto) {
        Long authenticatedCorpId = 1L;

        RecruitResponse.RecruitListDTO responseDTO = recruitService.save(dto, authenticatedCorpId);
        return ResponseEntity.ok(new ApiUtil<>(responseDTO));
    }

    // 공고 수정
    @PutMapping("/{id}")
    public ResponseEntity<ApiUtil<RecruitResponse.RecruitListDTO>> update(@PathVariable Long id,
                                                                          @RequestBody RecruitRequest.UpdateDTO dto) throws AccessDeniedException {
        Long authenticatedCorpId = 1L;

        RecruitResponse.RecruitListDTO responseDTO = recruitService.update(id, dto, authenticatedCorpId);
        return ResponseEntity.ok(new ApiUtil<>(responseDTO));
    }
}