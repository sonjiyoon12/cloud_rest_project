package com.cloud.cloud_rest.skill;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception400;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public List<SkillResponse.SkillListDTO> findAll() {
        log.info("전체 스킬 목록 조회 service 호출됨");
        return skillRepository.findAll().stream()
                .map(SkillResponse.SkillListDTO::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public SkillResponse.SkillDetailDTO save(SkillRequest.SkillSaveDTO reqDTO, SessionUser sessionUser) {
        log.info("스킬 저장 service 호출됨, requestDTO: {}", reqDTO);
        if (!"ADMIN".equals(sessionUser.getRole())) {
            throw new Exception403(SkillErr.SKILL_FORBIDDEN.getMessage());
        }
        // 1. 입력값 정규화 (공백 제거, 소문자 변환)
        String normalizedName = reqDTO.getName().trim().toLowerCase();

        // 2. 정규화된 이름으로 중복 확인 (대소문자 무시)
        if (skillRepository.findByNameIgnoreCase(normalizedName).isPresent()) {
            throw new Exception400(SkillErr.SKILL_ALREADY_EXISTS.getMessage());
        }

        // 3. 정규화된 이름으로 저장
        Skill skill = Skill.builder()
                .name(normalizedName)
                .build();
        Skill savedSkill = skillRepository.save(skill);

        // 4. DTO로 변환하여 반환
        return SkillResponse.SkillDetailDTO.of(savedSkill);
    }

    @Transactional
    public SkillResponse.SkillDetailDTO update(Long skillId, SkillRequest.SkillUpdateDTO reqDTO, SessionUser sessionUser) {
        log.info("스킬 수정 service 호출됨, skillId: {}, requestDTO: {}", skillId, reqDTO);
        if (!"ADMIN".equals(sessionUser.getRole())) {
            throw new Exception403(SkillErr.SKILL_FORBIDDEN.getMessage());
        }
        // 1. 수정할 스킬 조회 (없으면 404)
        Skill skillToUpdate = skillRepository.findById(skillId)
                .orElseThrow(() -> new Exception404(SkillErr.SKILL_NOT_FOUND.getMessage()));

        // 2. 새 이름 정규화
        String normalizedNewName = reqDTO.getName().trim().toLowerCase();

        // 3. 새 이름이 다른 스킬에서 이미 사용 중인지 확인
        skillRepository.findByNameIgnoreCase(normalizedNewName)
                .ifPresent(existingSkill -> {
                    // 찾은 스킬의 ID가 현재 수정하려는 스킬의 ID와 다르면, 이름이 중복된 것
                    if (!existingSkill.getSkillId().equals(skillId)) {
                        throw new Exception400(SkillErr.SKILL_ALREADY_EXISTS.getMessage());
                    }
                });

        // 4. 스킬 이름 업데이트
        skillToUpdate.updateName(normalizedNewName);

        // 5. DTO로 변환하여 반환 (@Transactional에 의해 변경 감지 후 자동 저장됨)
        return SkillResponse.SkillDetailDTO.of(skillToUpdate);
    }

    @Transactional
    public void delete(Long skillId, SessionUser sessionUser) {
        log.info("스킬 삭제 service 호출됨, skillId: {}", skillId);
        if (!"ADMIN".equals(sessionUser.getRole())) {
            throw new Exception403(SkillErr.SKILL_FORBIDDEN.getMessage());
        }
        // 1. 스킬 존재 여부 확인
        if (!skillRepository.existsById(skillId)) {
            throw new Exception404(SkillErr.SKILL_NOT_FOUND.getMessage());
        }

        // 2. 스킬이 Recruit, Resume, User 중 한 곳이라도 사용 중인지 확인
        boolean isUsed = skillRepository.isUsedInRecruitSkill(skillId) ||
                skillRepository.isUsedInResumeSkill(skillId) ||
                skillRepository.isUsedInUserSkill(skillId);

        if (isUsed) {
            // 사용 중이라면, SKILL_IN_USE 오류를 발생시키고 삭제를 막음
            throw new Exception400(SkillErr.SKILL_IN_USE.getMessage());
        }

        // 3. 어디에서도 사용되지 않을 때만 안전하게 삭제
        skillRepository.deleteById(skillId);
    }
}
