package com.cloud.cloud_rest.bulletin;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/bulletins")
public class BulletinController {

    private final BulletinService bulletinService;

    // 공지사항 전체 목록 조회
    @GetMapping
    public ResponseEntity<List<BulletinResponse.DTO>> findAll() {
        List<BulletinResponse.DTO> response = bulletinService.findAll();
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
            @RequestBody BulletinRequest.SaveDTO requestDTO,
            @RequestAttribute SessionUser sessionUser) throws IOException {
        BulletinResponse.DetailDTO response = bulletinService.save(requestDTO, sessionUser);
        return ResponseEntity.ok(response);
    }

    // 공지사항 수정 (ADMIN)
    @Auth(roles = {Role.ADMIN})
    @PutMapping("/{id}")
    public ResponseEntity<BulletinResponse.DetailDTO> update(
            @PathVariable Long id,
            @RequestBody BulletinRequest.UpdateDTO requestDTO,
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
