package com.cloud.cloud_rest.recruit;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.notify.NotifyService;
import com.cloud.cloud_rest.skill.Skill;
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
    private final NotifyService notifyService;
    private final RecruitServiceHelper recruitServiceHelper;


    //공고 저장
    @Transactional
    public RecruitResponse.RecruitListDTO save(RecruitRequest.RecruitSaveDTO dto, Long corpId, SessionUser sessionUser) {
        log.info("공고 등록 요청 - corpId: {}, title: {}", corpId, dto.getTitle());
        // 권한 검사
        recruitServiceHelper.checkSavePermission(corpId, sessionUser);
        // 기업 조회
        Corp corp = recruitServiceHelper.findCorpById(corpId);
        // 이미지 처리
        String savedFileName = recruitServiceHelper.saveImageFromBase64(dto.getImage());
        // 스킬 검증
        List<Skill> skills = recruitServiceHelper.validateSkillIds(dto.getSkillIds());
        // DTO를 엔티티로 변환
        Recruit recruit = dto.toEntity(corp, savedFileName);
        // 엔티티에 스킬 정보 업데이트
        recruit.updateSkills(skills);
        // 채용공고 엔티티 저장
        recruitRepository.save(recruit);
        // 알림 저장
        notifyService.save(recruit, dto.getMessage());
        // DTO로 변환하여 반환
        return RecruitResponse.RecruitListDTO.of(recruit);
    }

    //공고 수정
    @Transactional
    public RecruitResponse.RecruitListDTO update(Long recruitId, RecruitRequest.RecruitUpdateDTO dto, Long corpId, SessionUser sessionUser) {
        log.info("공고 수정 요청 - recruitId: {}, corpId: {}, title: {}", recruitId, corpId, dto.getTitle());
        // 공고 조회
        Recruit recruit = recruitServiceHelper.findRecruitById(recruitId);
        // 권한 검사
        recruitServiceHelper.checkUpdateOrDeletePermission(recruit, corpId, sessionUser);
        // 기존 이미지 경로 저장
        String oldImagePath = recruit.getImage();
        // 이미지 처리
        String savedFileName = recruitServiceHelper.updateImageFromBase64(dto.getImage(), oldImagePath);
        // 스킬 검증
        List<Skill> newSkills = recruitServiceHelper.validateSkillIds(dto.getSkillIds());
        // 엔티티 업데이트
        recruit.update(dto, savedFileName, newSkills);
        // DTO로 변환하여 반환
        return RecruitResponse.RecruitListDTO.of(recruit);
    }

    // 공고 삭제
    @Transactional
    public void recruitDelete(Long recruitId, Long corpId, SessionUser sessionUser) {
        log.info("공고 삭제 요청 - recruitId: {}, corpId: {}", recruitId, corpId);
        // 공고 조회
        Recruit recruit = recruitServiceHelper.findRecruitById(recruitId);
        // 권한 검사
        recruitServiceHelper.checkUpdateOrDeletePermission(recruit, corpId, sessionUser);
        // 이미지 처리
        recruitServiceHelper.deleteImage(recruit.getImage());
        // 채용공고 엔티티 삭제
        recruitRepository.delete(recruit);
    }

    // 전체 공고 목록 조회 (유료 공고 우선, 페이징)
    public Page<RecruitResponse.RecruitListDTO> findAll(Pageable pageable) {
        log.info("전체 공고 목록 조회 요청 - page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
        Page<Recruit> recruitPage = recruitRepository.findAllWithPaidPriority(pageable);
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

    // 키워드 검색
    public List<RecruitResponse.RecruitListDTO> searchByKeyword(String keyword) {
        log.info("키워드 검색 요청 - keyword: {}", keyword);
        List<Recruit> recruits = recruitRepository.findByKeyword(keyword);
        return recruits.stream()
                .map(RecruitResponse.RecruitListDTO::of)
                .collect(Collectors.toList());
    }
}