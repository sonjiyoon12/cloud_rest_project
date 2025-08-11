package com.cloud.cloud_rest.recruit;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.auth.Auth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecruitController {

    private final RecruitService recruitService;

    // 전체 공고 목록 조회 (페이징) - Public
    @GetMapping("/recruits")
    public ResponseEntity<?> findAll(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RecruitResponse.RecruitListDTO> responseDTOs = recruitService.findAll(pageable);
        return ResponseEntity.ok(new ApiUtil<>(responseDTOs));
    }

    // 공고 상세 조회 - Public
    @GetMapping("/recruits/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        RecruitResponse.RecruitDetailDTO responseDTO = recruitService.findById(id);
        return ResponseEntity.ok(new ApiUtil<>(responseDTO));
    }

    // 통합 검색 (제목, 회사명, 기술스택)
    @GetMapping("/recruits/search")
    public ResponseEntity<?> searchByKeyword(@RequestParam("keyword") String keyword) {
        List<RecruitResponse.RecruitListDTO> responseDTOs = recruitService.searchByKeyword(keyword);
        return ResponseEntity.ok(new ApiUtil<>(responseDTOs));
    }

    // 공고 저장 - 로그인 및 소유권 확인 필요
    @PostMapping("/corps/{corpId}/recruits")
    @Auth // 로그인 체크
    public ResponseEntity<?> save(@PathVariable Long corpId,
                                  @Valid @RequestBody RecruitRequest.RecruitSaveDTO dto,
                                  @RequestAttribute SessionUser sessionUser) {
        // 서비스에서 소유권 확인 (corpId == sessionUser.getId())
        RecruitResponse.RecruitListDTO responseDTO = recruitService.save(dto, corpId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(responseDTO));
    }

    // 공고 수정 - 로그인 및 소유권 확인 필요
    @PutMapping("/corps/{corpId}/recruits/{recruitId}")
    @Auth // 로그인 체크
    public ResponseEntity<?> update(@PathVariable Long corpId,
                                      @PathVariable Long recruitId,
                                      @Valid @RequestBody RecruitRequest.RecruitUpdateDTO dto,
                                      @RequestAttribute SessionUser sessionUser) {
        // 서비스에서 소유권 확인
        RecruitResponse.RecruitListDTO responseDTO = recruitService.update(recruitId, dto, corpId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(responseDTO));
    }

    //공고 삭제 - 로그인 및 소유권 확인 필요
    @DeleteMapping("/corps/{corpId}/recruits/{recruitId}")
    @Auth // 로그인 체크
    public ResponseEntity<?> delete(@PathVariable Long corpId,
                                      @PathVariable Long recruitId,
                                      @RequestAttribute SessionUser sessionUser) {
        // 서비스에서 소유권 확인
        recruitService.recruitDelete(recruitId, corpId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}
