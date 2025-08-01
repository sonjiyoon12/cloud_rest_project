package com.cloud.cloud_rest.skill;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SkillService {

    private final SkillRepository skillRepository;

    //전체조회
    public List<SkillResponse.SkillListDTO> findAll() {
        log.info("전체 스킬 조회 요청");

        List<Skill> skills = skillRepository.findAll();

        return skills.stream()
                .map(SkillResponse.SkillListDTO::of)
                .collect(Collectors.toList());
    }
}
