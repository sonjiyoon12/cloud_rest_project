package com.cloud.cloud_rest.corpskill;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpService;
import com.cloud.cloud_rest.user.Role;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CorpSkillService {

    private final CorpSkillRepository corpSkillRepository;
    private final CorpService corpService;
    private final UserRepository userRepository;

    public List<CorpSkillResponse.CorpSkillDTO> getMatchedCorpSkills(SessionUser sessionUser){

        if(!sessionUser.getRole().equals(Role.CORP)){
            throw new Exception403("기업 만 접근 가능합니다");
        }

        Corp corp = corpService.getCorpId(sessionUser.getId());

        List<String> skillNames = corpSkillRepository.findByCorpMatch(corp);

        List<User> matchingCorps = userRepository.findByMatchingSkills(skillNames);

        return matchingCorps.stream()
                .map(CorpSkillResponse.CorpSkillDTO::new)
                .toList();
    }
}
