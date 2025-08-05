package com.cloud.cloud_rest.skill;

import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.auth.Auth;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SkillController {

    private final SkillService skillService;

    //스킬 목록
    @GetMapping("/api/skills")
    public ResponseEntity<?> findAll() {
        List<SkillResponse.SkillListDTO> respDTO = skillService.findAll();
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 스킬 저장
    @PostMapping("/api/skills")
    @Auth(role = "ADMIN") // 관리자
    public ResponseEntity<?> save(@RequestBody @Valid SkillRequest.SkillSaveDTO reqDTO) {
        SkillResponse.SkillDetailDTO respDTO = skillService.save(reqDTO);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 스킬 수정
    @PutMapping("/api/skills/{id}")
    @Auth(role = "ADMIN") // 관리자
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid SkillRequest.SkillUpdateDTO reqDTO) {
        SkillResponse.SkillDetailDTO respDTO = skillService.update(id, reqDTO);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 스킬 삭제
    @DeleteMapping("/api/skills/{id}")
    @Auth(role = "ADMIN") // 관리자
    public ResponseEntity<?> delete(@PathVariable Long id) {
        skillService.delete(id);
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}
