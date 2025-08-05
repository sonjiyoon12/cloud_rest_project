package com.cloud.cloud_rest.skill;

import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest._global.SessionUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SkillController {

    private final SkillService skillService;

    //스킬 목록
    @GetMapping("/skills")
    public ResponseEntity<?> findAll() {
        List<SkillResponse.SkillListDTO> respDTO = skillService.findAll();
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 스킬 저장
    @PostMapping("/skills")
    @Auth
    public ResponseEntity<?> save(@RequestBody @Valid SkillRequest.SkillSaveDTO reqDTO, @RequestAttribute("sessionUser") SessionUser sessionUser) {
        SkillResponse.SkillDetailDTO respDTO = skillService.save(reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 스킬 수정
    @PutMapping("/skills")
    @Auth
    public ResponseEntity<?> update(@RequestParam Long id, @RequestBody @Valid SkillRequest.SkillUpdateDTO reqDTO, @RequestAttribute("sessionUser") SessionUser sessionUser) {
        SkillResponse.SkillDetailDTO respDTO = skillService.update(id, reqDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(respDTO));
    }

    // 스킬 삭제
    @DeleteMapping("/skills")
    @Auth
    public ResponseEntity<?> delete(@RequestParam Long id, @RequestAttribute("sessionUser") SessionUser sessionUser) {
        skillService.delete(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(null));
    }
}
