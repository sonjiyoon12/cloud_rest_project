package com.cloud.cloud_rest.skill;

import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest.user.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    //스킬 목록
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<SkillResponse.SkillListDTO> respDTO = skillService.findAll();
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 스킬 저장
    @PostMapping
    @Auth(roles = {Role.ADMIN})
    public ResponseEntity<?> save(@RequestBody @Valid SkillRequest.SkillSaveDTO reqDTO, @RequestAttribute("sessionUser") SessionUser sessionUser) {
        SkillResponse.SkillDetailDTO respDTO = skillService.save(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 스킬 수정
    @PutMapping("/{skillId}")
    @Auth(roles = {Role.ADMIN})
    public ResponseEntity<?> update(@PathVariable Long skillId, @RequestBody @Valid SkillRequest.SkillUpdateDTO reqDTO, @RequestAttribute("sessionUser") SessionUser sessionUser) {
        SkillResponse.SkillDetailDTO respDTO = skillService.update(skillId, reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 스킬 삭제
    @DeleteMapping("/{skillId}")
    @Auth(roles = {Role.ADMIN})
    public ResponseEntity<?> delete(@PathVariable Long skillId, @RequestAttribute("sessionUser") SessionUser sessionUser) {
        skillService.delete(skillId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}
