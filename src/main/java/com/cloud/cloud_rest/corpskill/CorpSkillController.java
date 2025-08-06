package com.cloud.cloud_rest.corpskill;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest.userskill.UserSkillResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/corp-skills")
@RequiredArgsConstructor
public class CorpSkillController {

    private final CorpSkillService corpSkillService;

    @Auth
    @GetMapping("/match")
    public ResponseEntity<?> getMatchedCorpSkills(@RequestAttribute("sessionUser") SessionUser sessionUser){
        List<CorpSkillResponse.CorpSkillDTO> matchedCorpSkills = corpSkillService.getMatchedCorpSkills(sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(matchedCorpSkills));
    }
}
