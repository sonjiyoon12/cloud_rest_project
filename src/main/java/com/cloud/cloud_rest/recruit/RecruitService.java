package com.cloud.cloud_rest.recruit;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpRepository;
import com.cloud.cloud_rest.recruitskill.RecruitSkill;
import com.cloud.cloud_rest.skill.Skill;
import com.cloud.cloud_rest.skill.SkillRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
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
    public RecruitResponse.RecruitListDTO save(RecruitRequest.RecruitSaveDTO dto, Long corpId) {
        log.info("공고 등록 요청 - corpId: {}, title: {}", corpId, dto.getTitle());

        // 인증된 사용자의 corpId를 사용하여 Corp 엔티티 조회
        Corp corp = corpRepository.findById(corpId)
                .orElseThrow(() -> new EntityNotFoundException(RecruitErr.CORP_NOT_FOUND + corpId));

        Recruit recruit = dto.toEntity(corp);
        recruitRepository.save(recruit);

        // 스킬 처리 로직
        updateRecruitSkills(recruit, dto.getSkillIds());

        // DTO로 변환하여 반환
        return RecruitResponse.RecruitListDTO.of(recruit);
    }

    //공고 수정
    @Transactional
    public RecruitResponse.RecruitListDTO update(Long recruitId, RecruitRequest.RecruitUpdateDTO dto, Long corpId) throws AccessDeniedException {
        log.info("공고 수정 요청 - recruitId: {}, title: {}", recruitId, dto.getTitle());

        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> new EntityNotFoundException(RecruitErr.RECRUIT_NOT_FOUND + recruitId));

        // 소유권 확인
        if (!recruit.getCorp().getCorpId().equals(corpId)) {
            throw new AccessDeniedException(RecruitErr.ACCESS_DENIED);
        }

        // 엔티티 업데이트
        recruit.update(dto.getTitle(), dto.getContent(), dto.getDeadline());

        // 스킬 처리 로직
        updateRecruitSkills(recruit, dto.getSkillIds());

        // DTO로 변환하여 반환 (컴파일 오류 수정 및 일관성 유지)
        return RecruitResponse.RecruitListDTO.of(recruit);
    }

    // 공고 삭제
    @Transactional
    public void recruitDelete(Long recruitId, Long corpId) throws AccessDeniedException {
        log.info("공고 삭제 요청 - recruitId: {}, corpId: {}", recruitId, corpId);

        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> new EntityNotFoundException(RecruitErr.RECRUIT_NOT_FOUND + recruitId));

        // isOwner 메소드 대신 직접 ID를 비교하여 Corp 엔티티를 다시 조회하는 오버헤드를 줄일 수 있습니다.
        if (!recruit.getCorp().getCorpId().equals(corpId)) {
            throw new AccessDeniedException(RecruitErr.ACCESS_DENIED);
        }

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
                .orElseThrow(() -> new EntityNotFoundException(RecruitErr.RECRUIT_NOT_FOUND + recruitId));

        // DTO로 변환하여 반환
        return RecruitResponse.RecruitDetailDTO.of(recruit);
    }

    // 기업별 전체 공고조회
    public List<RecruitResponse.RecruitListDTO> findByCorpId(Long corpId) {
        log.info("기업별 공고 조회 요청 - corpId: {}", corpId);
        List<Recruit> recruits = recruitRepository.findByCorpId(corpId);
        // 엔티티 리스트를 DTO 리스트로 변환하여 반환
        return recruits.stream()
                .map(RecruitResponse.RecruitListDTO::of)
                .collect(Collectors.toList());
    }

    // 공고갯수 가져오기
    public Long countByCorpId(Long corpId) {
        log.info("기업별 공고 갯수 조회 요청 - corpId: {}", corpId);
        return recruitRepository.countByCorpId(corpId);
    }

    ///     ///     ///     ///     ///     ///     ///     ///     ///

    //스킬처리 헬퍼
    private void updateRecruitSkills(Recruit recruit, List<Long> skillIds) {
        // 기존 스킬 모두 제거 (orphanRemoval=true 옵션으로 DB에서도 삭제됨)
        recruit.getRecruitSkills().clear();

        if (skillIds != null && !skillIds.isEmpty()) {
            List<Skill> skills = skillRepository.findAllById(skillIds);
            // skillIds로 조회된 Skill이 없는 경우에 대한 예외처리도 고려해볼 수 있습니다.

            List<RecruitSkill> newRecruitSkills = skills.stream()
                    .map(skill -> new RecruitSkill(recruit, skill))
                    .collect(Collectors.toList());

            recruit.getRecruitSkills().addAll(newRecruitSkills);
        }
    }
}
