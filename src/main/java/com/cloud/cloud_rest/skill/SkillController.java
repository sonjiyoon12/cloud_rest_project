package com.cloud.cloud_rest.skill;

import com.cloud.cloud_rest._global._core.common.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/skills")
public class SkillController {

    private final SkillService skillService;

    //전체 스킬조회
    @GetMapping
    public ResponseEntity<ApiUtil<List<SkillResponse.SkillListDTO>>> findAll() {
        List<SkillResponse.SkillListDTO> dtos = skillService.findAll();

        return ResponseEntity.ok(new ApiUtil<>(dtos));

    }

}
