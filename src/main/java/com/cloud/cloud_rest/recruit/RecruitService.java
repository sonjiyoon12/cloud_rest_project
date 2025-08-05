package com.cloud.cloud_rest.recruit;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpRepository;
import com.cloud.cloud_rest.recruitskill.RecruitSkill;
import com.cloud.cloud_rest.skill.Skill;
import com.cloud.cloud_rest.skill.SkillErr;
import com.cloud.cloud_rest.skill.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecruitService {

    private final RecruitRepository recruitRepository;
    private final CorpRepository corpRepository;
    private final SkillRepository skillRepository;


    //공고 저장
    @Transactional
    public RecruitResponse.RecruitListDTO save(RecruitRequest.RecruitSaveDTO dto, Long corpId, SessionUser sessionUser) {
        log.info("공고 등록 요청 - corpId: {}, title: {}", corpId, dto.getTitle());

        if (!corpId.equals(sessionUser.getId())) {
            throw new Exception403(RecruitErr.RECRUIT_FORBIDDEN.getMessage());
        }

        Corp corp = corpRepository.findById(corpId)
                .orElseThrow(() -> new Exception404(RecruitErr.CORP_NOT_FOUND.getMessage()));

        Recruit recruit = dto.toEntity(corp);
        recruitRepository.save(recruit);

        updateRecruitSkills(recruit, dto.getSkillIds());

        return RecruitResponse.RecruitListDTO.of(recruit);
    }

    //공고 수정
    @Transactional
    public RecruitResponse.RecruitListDTO update(Long recruitId, RecruitRequest.RecruitUpdateDTO dto, Long corpId, SessionUser sessionUser) {
        log.info("공고 수정 요청 - recruitId: {}, corpId: {}, title: {}", recruitId, corpId, dto.getTitle());

        if (!corpId.equals(sessionUser.getId())) {
            throw new Exception403(RecruitErr.RECRUIT_FORBIDDEN.getMessage());
        }

        // [개선] ID와 소유주 ID로 한번에 조회 및 검증. 실패 시, 공고가 없거나 남의 공고이므로 403 예외를 던집니다.
        Recruit recruit = recruitRepository.findByIdAndCorpId(recruitId, corpId)
                .orElseThrow(() -> new Exception403(RecruitErr.RECRUIT_FORBIDDEN.getMessage()));

        // 엔티티 업데이트
        recruit.update(dto.getTitle(), dto.getContent(), dto.getDeadline());

        // 스킬 처리 로직
        updateRecruitSkills(recruit, dto.getSkillIds());

        return RecruitResponse.RecruitListDTO.of(recruit);
    }

    // 공고 삭제
    @Transactional
    public void recruitDelete(Long recruitId, Long corpId, SessionUser sessionUser) {
        log.info("공고 삭제 요청 - recruitId: {}, corpId: {}", recruitId, corpId);

        if (!corpId.equals(sessionUser.getId())) {
            throw new Exception403(RecruitErr.RECRUIT_FORBIDDEN.getMessage());
        }

        // [개선] ID와 소유주 ID로 한번에 조회 및 검증. 실패 시 403 예외를 던집니다.
        Recruit recruit = recruitRepository.findByIdAndCorpId(recruitId, corpId)
                .orElseThrow(() -> new Exception403(RecruitErr.RECRUIT_FORBIDDEN.getMessage()));

        recruitRepository.delete(recruit);
    }

    // 전체 공고 목록 조회 (페이징)
    public Page<RecruitResponse.RecruitListDTO> findAll(Pageable pageable) {
        log.info("전체 공고 목록 조회 요청 - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<Recruit> recruitPage = recruitRepository.findAll(pageable);
        return recruitPage.map(RecruitResponse.RecruitListDTO::of);
    }

    // 공고 상세 조회
    public RecruitResponse.RecruitDetailDTO findById(Long recruitId) {
        log.info("공고 상세 조회 요청 - recruitId: {}", recruitId);
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> new Exception404(RecruitErr.RECRUIT_NOT_FOUND.getMessage()));

        return RecruitResponse.RecruitDetailDTO.of(recruit);
    }

    // 기업별 전체 공고조회
    public List<RecruitResponse.RecruitListDTO> findByCorpId(Long corpId) {
        log.info("기업별 공고 조회 요청 - corpId: {}", corpId);
        List<Recruit> recruits = recruitRepository.findByCorpId(corpId);
        return recruits.stream()
                .map(RecruitResponse.RecruitListDTO::of)
                .collect(Collectors.toList());
    }

    // 공고갯수 가져오기
    public Long countByCorpId(Long corpId) {
        log.info("기업별 공고 갯수 조회 요청 - corpId: {}", corpId);
        return recruitRepository.countByCorpId(corpId);
    }


    // 스킬처리 헬퍼
    private void updateRecruitSkills(Recruit recruit, List<Long> skillIds) {
        recruit.getRecruitSkills().clear();

        if (skillIds == null || skillIds.isEmpty()) {
            return;
        }

        List<Skill> foundSkills = skillRepository.findAllById(skillIds);

        if (foundSkills.size() != skillIds.size()) {
            throw new Exception404(SkillErr.SKILL_NOT_FOUND.getMessage());
        }

        List<RecruitSkill> newRecruitSkills = foundSkills.stream()
                .map(skill -> new RecruitSkill(recruit, skill))
                .collect(Collectors.toList());

        recruit.getRecruitSkills().addAll(newRecruitSkills);
    }
}
