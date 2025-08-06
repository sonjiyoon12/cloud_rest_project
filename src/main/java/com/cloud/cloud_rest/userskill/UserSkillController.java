package com.cloud.cloud_rest.userskill;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.auth.Auth;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user-skills")
@RequiredArgsConstructor
public class UserSkillController {
    private final UserSkillService userSkillService;

    @Auth
    @GetMapping("/match")
    public ResponseEntity<?> getMatchedUserSkills(@RequestAttribute("sessionUser")SessionUser sessionUser){
        List<UserSkillResponse.UserSkillDTO> matchedUserSkills = userSkillService.getMatchedUserSkills(sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(matchedUserSkills));
    }
}
