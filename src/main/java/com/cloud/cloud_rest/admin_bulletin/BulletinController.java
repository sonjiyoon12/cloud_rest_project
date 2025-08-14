package com.cloud.cloud_rest.admin_bulletin;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest.user.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; // Valid import 추가

import java.io.IOException;

@Tag(name = "Bulletin : 관리자용 공지사항 API", description = "관리자가 공지사항을 관리하게 해주는 API입니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bulletins")
public class BulletinController {

    private final BulletinService bulletinService;

    @Operation(summary = "[전체] 공지사항 전체 목록 조회", description = "페이징 처리된 전체 공지사항 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<Page<BulletinResponse.DTO>> findAll(
            @PageableDefault(size = 10, sort = "bulletinId", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BulletinResponse.DTO> response = bulletinService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "[전체] 공지사항 상세 정보 조회", description = "특정 공지사항의 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<BulletinResponse.DetailDTO> findById(
            @PathVariable Long id) {
        BulletinResponse.DetailDTO response = bulletinService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "[관리자] 공지사항 생성", description = "관리자가 새로운 공지사항을 등록합니다.")
    @Auth(roles = {Role.ADMIN})
    @PostMapping
    public ResponseEntity<BulletinResponse.DetailDTO> save(
            @RequestBody @Valid BulletinRequest.SaveDTO requestDTO, // @Valid 추가
            @RequestAttribute SessionUser sessionUser) throws IOException {
        BulletinResponse.DetailDTO response = bulletinService.save(requestDTO, sessionUser);
        return ResponseEntity.ok(response);
    }

    // [Swagger] API 문서의 제목과 설명을 정의합니다.
    @Operation(summary = "[관리자] 공지사항 수정", description = "관리자가 기존 공지사항을 수정합니다.")
    // [Auth] 이 API는 ADMIN 역할을 가진 사용자만 호출할 수 있도록 제한합니다. (AuthInterceptor가 처리)
    @Auth(roles = {Role.ADMIN})
    // HTTP PUT 요청을 "/api/bulletins/{id}" 경로와 매핑합니다.
    @PutMapping("/{id}")
    public ResponseEntity<BulletinResponse.DetailDTO> update(
            // URL 경로의 {id} 값을 Long 타입의 id 파라미터로 받습니다. (예: /api/bulletins/1)
            @PathVariable Long id,
            // [DTO] HTTP 요청의 본문(body)에 담긴 JSON 데이터를 BulletinRequest.UpdateDTO 객체로 변환합니다.
            // [Validation] @Valid 어노테이션을 통해 UpdateDTO에 정의된 유효성 검사(예: @NotEmpty)를 자동으로 수행합니다.
            @RequestBody @Valid BulletinRequest.UpdateDTO requestDTO,
            // [Auth] AuthInterceptor에서 검증하고 저장한 로그인 사용자 정보(SessionUser)를 파라미터로 주입받습니다.
            @RequestAttribute SessionUser sessionUser) {
        BulletinResponse.DetailDTO response = bulletinService.update(id, requestDTO, sessionUser);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "[관리자] 공지사항 삭제", description = "관리자가 기존 공지사항을 삭제합니다.")
    @Auth(roles = {Role.ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestAttribute SessionUser sessionUser) {
        bulletinService.delete(id, sessionUser);
        return ResponseEntity.ok().build();
    }
}
