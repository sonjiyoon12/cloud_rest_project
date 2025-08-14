package com.cloud.cloud_rest.recruit;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest.user.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Recruit : 기업용 채용공고 API", description = "기업회원(유저)에게 채용공고 관련 API를 제공합니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RecruitController {

    private final RecruitService recruitService;

    @Operation(summary = "전체 공고 목록 조회", description = "페이징 처리된 전체 공고 목록을 조회합니다.")
    @GetMapping("/recruits")
    public ResponseEntity<?> findAll(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<RecruitResponse.RecruitListDTO> responseDTOs = recruitService.findAll(pageable);
        return ResponseEntity.ok(new ApiUtil<>(responseDTOs));
    }

    @Operation(summary = "공고 상세 조회", description = "특정 공고의 상세 정보를 조회합니다.")
    @GetMapping("/recruits/{recruitId}")
    public ResponseEntity<?> findById(@PathVariable Long recruitId) {
        RecruitResponse.RecruitDetailDTO responseDTO = recruitService.findById(recruitId);
        return ResponseEntity.ok(new ApiUtil<>(responseDTO));
    }

    @Operation(summary = "공고 검색", description = "키워드를 사용하여 제목, 회사명, 기술스택으로 공고를 검색합니다.")
    @GetMapping("/recruits/search")
    public ResponseEntity<?> searchByKeyword(@RequestParam("keyword") String keyword) {
        List<RecruitResponse.RecruitListDTO> responseDTOs = recruitService.searchByKeyword(keyword);
        return ResponseEntity.ok(new ApiUtil<>(responseDTOs));
    }

    @Operation(summary = "공고 등록", description = "기업 회원이 새로운 공고를 등록합니다.", security = @SecurityRequirement(name = "jwt"))
    @PostMapping("/corps/{corpId}/recruits")
    @Auth(roles = {Role.CORP, Role.ADMIN})
    public ResponseEntity<?> save(@PathVariable Long corpId,
                                  @Valid @RequestBody RecruitRequest.RecruitSaveDTO dto,
                                  @RequestAttribute SessionUser sessionUser) {
        RecruitResponse.RecruitListDTO responseDTO = recruitService.save(dto, corpId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(responseDTO));
    }

    // [Swagger] API 문서의 제목과 설명 추가
    @Operation(
            summary = "공고 수정",
            description = "기업 회원이 기존 공고를 수정합니다.",
            security = @SecurityRequirement(name = "jwt"))
    @PutMapping("/corps/{corpId}/recruits/{recruitId}")
    // [Auth] CORP, ADMIN 사용자만 호출가능 => AuthInterceptor가 처리
    @Auth(roles = {Role.CORP, Role.ADMIN})
    public ResponseEntity<?> update(
            @PathVariable Long corpId,
            @PathVariable Long recruitId,
            // [Validation] DTO에 정의된 유효성 검사 자동 수행
            @Valid @RequestBody RecruitRequest.RecruitUpdateDTO dto,
            // AuthInterceptor가 검증한 SessionUser 받아옴
            @RequestAttribute SessionUser sessionUser) {
        // [Service] 비즈니스 로직 서비스에 위임
        RecruitResponse.RecruitListDTO responseDTO =
                recruitService.update(recruitId, dto, corpId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(responseDTO));
    }

    @Operation(summary = "공고 삭제", description = "기업 회원이 기존 공고를 삭제합니다.", security = @SecurityRequirement(name = "jwt"))
    @DeleteMapping("/corps/{corpId}/recruits/{recruitId}")
    @Auth(roles = {Role.CORP, Role.ADMIN})
    public ResponseEntity<?> delete(@PathVariable Long corpId,
                                    @PathVariable Long recruitId,
                                    @RequestAttribute SessionUser sessionUser) {
        recruitService.recruitDelete(recruitId, corpId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}
