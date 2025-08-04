package com.cloud.cloud_rest.skill;

import com.cloud.cloud_rest.recruitskill.RecruitSkillRepository;
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
    private final RecruitSkillRepository recruitSkillRepository;


    //전체조회
    public List<SkillResponse.SkillListDTO> findAll() {
        log.info("전체 스킬 조회 요청");

        List<Skill> skills = skillRepository.findAll();

        return skills.stream()
                .map(SkillResponse.SkillListDTO::of)
                .collect(Collectors.toList());
    }

    //관리자용 스킬 추가
    @Transactional
    public void save(String name) {
        //중복체크
        skillRepository.findByNameIgnoreCase(name).ifPresent(skill -> {
            throw new RuntimeException("이미 존재하는 기술입니다");
        });

        Skill newSkill = Skill.builder()
                .name(name)
                .build();
        skillRepository.save(newSkill);
    }

    //관리자용 스킬 수정
    @Transactional
    public void update(Long skillId, String name) {

        //스킬 존재여부 확인 + 객체 생성
        Skill skill = skillRepository.findById(skillId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 기술입니다"));

        //스킬 중복여부 체크
        skillRepository.findByNameIgnoreCase(name)
                .ifPresent(skill1 -> {
                    if (!skill1.getSkillId().equals(skillId)) {
                        throw new RuntimeException("이미 존재하는 기술입니다");
                    }
                });

        //업데이트 더팅체킹
        skill.updateName(name);
    }

    //관리자용 스킬삭제
    @Transactional
    public void delete(Long skillId) {
        //스킬 존재여부만 체크
        if (!skillRepository.existsById(skillId)) {
            throw new RuntimeException("존재하지 않는 기술입니다");
        }

        //스킬 사용여부 체크
        if (recruitSkillRepository.existsBySkillSkillId(skillId)) {
            throw new RuntimeException("현재 사용중인 스킬입니다");
        }


    }
}


