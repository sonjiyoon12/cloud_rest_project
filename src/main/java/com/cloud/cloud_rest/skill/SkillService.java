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

    //관리자용 수정기능
    @Transactional
    public void addSkill(String name) {
        //중복체크
        skillRepository.findByNameIgnoreCase(name).ifPresent(skill -> {
            throw new RuntimeException("이미 존재하는 기술입니다");
        });

        Skill newSkill = Skill.builder()
    }
}
