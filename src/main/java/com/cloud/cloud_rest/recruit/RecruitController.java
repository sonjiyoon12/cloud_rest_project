package com.cloud.cloud_rest.recruit;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recruits")
public class RecruitController {

    private final RecruitService recruitService;

    // 전체 공고 목록 조회 (페이징)
    @GetMapping
    public ResponseEntity<ApiUtil<Page<RecruitResponse.RecruitListDTO>>> findAll(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RecruitResponse.RecruitListDTO> responseDTOs = recruitService.findAll(pageable);
        return ResponseEntity.ok(new ApiUtil<>(responseDTOs));
    }

    // 공고 저장
    @PostMapping
    @Auth(role = "CORP") // 기업 회원
    public ResponseEntity<ApiUtil<RecruitResponse.RecruitListDTO>> save(@RequestBody RecruitRequest.RecruitSaveDTO dto,
                                                                        @RequestAttribute SessionUser sessionUser) {
        Long authenticatedCorpId = sessionUser.getId();

        RecruitResponse.RecruitListDTO responseDTO = recruitService.save(dto, authenticatedCorpId);
        return ResponseEntity.ok(new ApiUtil<>(responseDTO));
    }

    // 공고 수정
    @PutMapping("/{id}")
    @Auth(role = "CORP") // 기업 회원
    public ResponseEntity<ApiUtil<RecruitResponse.RecruitListDTO>> update(@PathVariable Long id,
                                                                          @RequestBody RecruitRequest.RecruitUpdateDTO dto,
                                                                          @RequestAttribute SessionUser sessionUser) throws AccessDeniedException {
        Long authenticatedCorpId = sessionUser.getId();

        RecruitResponse.RecruitListDTO responseDTO = recruitService.update(id, dto, authenticatedCorpId);
        return ResponseEntity.ok(new ApiUtil<>(responseDTO));
    }

    // 공고 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ApiUtil<RecruitResponse.RecruitDetailDTO>> findById(@PathVariable Long id) {
        RecruitResponse.RecruitDetailDTO responseDTO = recruitService.findById(id);
        return ResponseEntity.ok(new ApiUtil<>(responseDTO));
    }

    //공고 삭제
    @DeleteMapping("/{id}")
    @Auth(role = "CORP") // 기업 회원
    public ResponseEntity<ApiUtil<String>> delete(@PathVariable Long id,
                                                  @RequestAttribute SessionUser sessionUser) throws AccessDeniedException {
        Long authenticatedCorpId = sessionUser.getId();

        recruitService.recruitDelete(id, authenticatedCorpId);
        return ResponseEntity.ok(new ApiUtil<>("공고 삭제 완료"));

    }
}
