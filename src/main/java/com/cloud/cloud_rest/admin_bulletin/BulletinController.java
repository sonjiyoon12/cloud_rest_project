package com.cloud.cloud_rest.admin_bulletin;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; // Valid import 추가

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/bulletins")
public class BulletinController {

    private final BulletinService bulletinService;

    // 공지사항 전체 목록 조회
    @GetMapping
    public ResponseEntity<Page<BulletinResponse.DTO>> findAll(
            @PageableDefault(size = 10, sort = "bulletinId", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<BulletinResponse.DTO> response = bulletinService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    // 공지사항 상세 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<BulletinResponse.DetailDTO> findById(
            @PathVariable Long id) {
        BulletinResponse.DetailDTO response = bulletinService.findById(id);
        return ResponseEntity.ok(response);
    }

    // 공지사항 생성 (ADMIN)
    @Auth(roles = {Role.ADMIN})
    @PostMapping
    public ResponseEntity<BulletinResponse.DetailDTO> save(
            @RequestBody @Valid BulletinRequest.SaveDTO requestDTO, // @Valid 추가
            @RequestAttribute SessionUser sessionUser) throws IOException {
        BulletinResponse.DetailDTO response = bulletinService.save(requestDTO, sessionUser);
        return ResponseEntity.ok(response);
    }

    // 공지사항 수정 (ADMIN)
    @Auth(roles = {Role.ADMIN})
    @PutMapping("/{id}")
    public ResponseEntity<BulletinResponse.DetailDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid BulletinRequest.UpdateDTO requestDTO, // @Valid 추가
            @RequestAttribute SessionUser sessionUser) {
        BulletinResponse.DetailDTO response = bulletinService.update(id, requestDTO, sessionUser);
        return ResponseEntity.ok(response);
    }

    // 공지사항 삭제 (ADMIN)
    @Auth(roles = {Role.ADMIN})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestAttribute SessionUser sessionUser) {
        bulletinService.delete(id, sessionUser);
        return ResponseEntity.ok().build();
    }
}
