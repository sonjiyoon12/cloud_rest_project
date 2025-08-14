package com.cloud.cloud_rest.skill;

import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest.user.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Skill : 관리자용 기술스택 API", description = "관리자가 기술스택을 관리하게 해주는 API입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    @Operation(summary = "[전체] 전체 기술스택 목록 조회", description = "모든 기술스택 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<SkillResponse.SkillListDTO> respDTO = skillService.findAll();
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    @Operation(summary = "[관리자] 기술스택 저장", description = "관리자가 새로운 기술스택을 등록합니다.")
    @PostMapping
    @Auth(roles = {Role.ADMIN})
    public ResponseEntity<?> save(@RequestBody @Valid SkillRequest.SkillSaveDTO reqDTO, @RequestAttribute("sessionUser") SessionUser sessionUser) {
        SkillResponse.SkillDetailDTO respDTO = skillService.save(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    @Operation(summary = "[관리자] 기술스택 수정", description = "관리자가 기존 기술스택을 수정합니다.")
    @PutMapping("/{skillId}")
    @Auth(roles = {Role.ADMIN})
    public ResponseEntity<?> update(@PathVariable Long skillId, @RequestBody @Valid SkillRequest.SkillUpdateDTO reqDTO, @RequestAttribute("sessionUser") SessionUser sessionUser) {
        SkillResponse.SkillDetailDTO respDTO = skillService.update(skillId, reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    @Operation(summary = "[관리자] 기술스택 삭제", description = "관리자가 기존 기술스택을 삭제합니다.")
    @DeleteMapping("/{skillId}")
    @Auth(roles = {Role.ADMIN})
    public ResponseEntity<?> delete(@PathVariable Long skillId, @RequestAttribute("sessionUser") SessionUser sessionUser) {
        skillService.delete(skillId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}
