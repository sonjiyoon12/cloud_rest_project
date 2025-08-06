package com.cloud.cloud_rest.userskill;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpRepository;
import com.cloud.cloud_rest.corp.CorpResponse;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import com.cloud.cloud_rest.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserSkillService {

    private final UserSkillRepository userSkillRepository;
    private final UserService userService;
    private final CorpRepository corpRepository;

    public List<UserSkillResponse.UserSkillDTO> getMatchedUserSkills(SessionUser sessionUser){
        User user = userService.getUserId(sessionUser.getId());

        if(!sessionUser.getRole().equals("USER")){
            throw new Exception403("유저만 접근 가능합니다");
        }
        
        List<String> skillNames = userSkillRepository.findByUserMatch(user);


        List<Corp> matchingCorps = corpRepository.findByMatchingSkills(skillNames);


        return matchingCorps.stream()
                .map(UserSkillResponse.UserSkillDTO::new)
                .toList();
    }
}
