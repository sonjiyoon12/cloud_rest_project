package com.cloud.cloud_rest.userskill;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.utils.AuthorizationUtil;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpRepository;
import com.cloud.cloud_rest.corp.CorpResponse;
import com.cloud.cloud_rest.recruit.Recruit;
import com.cloud.cloud_rest.recruit.RecruitRepository;
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
    private final RecruitRepository recruitRepository;

    // 유저랑 기업에 스킬이 같으면 같으면 매칭 (유저를 기준)
    public List<UserSkillResponse.UserSkillDTO> getMatchedUserSkills(SessionUser sessionUser){
        User user = userService.getUserId(sessionUser.getId());

        AuthorizationUtil.validateUserAccess(user.getUserId(),sessionUser);
        
        List<String> skillNames = userSkillRepository.findByUserMatch(user);


        List<Corp> matchingCorps = corpRepository.findByMatchingSkills(skillNames);


        return matchingCorps.stream()
                .map(UserSkillResponse.UserSkillDTO::new)
                .toList();
    }

    // 유저랑 공고에 대한 스킬이 같으면 매칭 (유저를 기준)
    public List<UserSkillResponse.RecruitSkillDTO> getMatchedRecruitSkills(SessionUser sessionUser){
        User user = userService.getUserId(sessionUser.getId());

        AuthorizationUtil.validateUserAccess(user.getUserId(),sessionUser);

        List<String> skillNames = userSkillRepository.findByUserMatch(user);


        List<Recruit> matchingCorps = recruitRepository.findByMatchingSkills(skillNames);


        return matchingCorps.stream()
                .map(UserSkillResponse.RecruitSkillDTO::new)
                .toList();
    }
}
