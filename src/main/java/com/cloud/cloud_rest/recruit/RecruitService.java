package com.cloud.cloud_rest.recruit;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest._global.utils.Base64FileConverterUtil;
import com.cloud.cloud_rest._global.utils.FileUploadUtil;
import com.cloud.cloud_rest._global.utils.UploadProperties;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpRepository;
import com.cloud.cloud_rest.noti.NotiService;
import com.cloud.cloud_rest.skill.Skill;
import com.cloud.cloud_rest.skill.SkillErr;
import com.cloud.cloud_rest.skill.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
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
    private final NotiService notiService;
    private final FileUploadUtil fileUploadUtil; // 이미지 저장 및 삭제 기능
    private final UploadProperties uploadPath; // 이미지 저장 경로 설정


    //공고 저장
    @Transactional
    public RecruitResponse.RecruitListDTO save(RecruitRequest.RecruitSaveDTO dto, Long corpId, SessionUser sessionUser) {
        log.info("공고 등록 요청 - corpId: {}, title: {}", corpId, dto.getTitle());

        if (!corpId.equals(sessionUser.getId())) {
            throw new Exception403(RecruitErr.RECRUIT_FORBIDDEN.getMessage());
        }

        Corp corp = corpRepository.findById(corpId)
                .orElseThrow(() -> new Exception404("해당 기업을 찾을 수 없습니다."));

        String savedFileName = null;
        if (dto.getImage() != null && !dto.getImage().isBlank()) {
            try {
                MultipartFile targetFile = Base64FileConverterUtil.convert(dto.getImage());
                savedFileName = fileUploadUtil.uploadProfileImage(targetFile, uploadPath.getRecruitDir());
            } catch (IOException e) {
                throw new RuntimeException("이미지 업로드에 실패했습니다.", e);
            }
        }

        //dto.toEntity()에 savedFileName을 전달하여 위임합니다.
        Recruit recruit = dto.toEntity(corp, savedFileName);

        // DTO로부터 Skill 엔티티 목록을 조회
        List<Skill> skills = skillRepository.findAllById(dto.getSkillIds());
        if (skills.size() != dto.getSkillIds().size()) {
            throw new Exception404(SkillErr.SKILL_NOT_FOUND.getMessage());
        }

        // 엔티티에 스킬 업데이트 위임
        recruit.updateSkills(skills);

        recruitRepository.save(recruit);

        //알람 저장
        notiService.save(recruit, dto.getMessage());

        return RecruitResponse.RecruitListDTO.of(recruit);
    }

    //공고 수정
    @Transactional
    public RecruitResponse.RecruitListDTO update(Long recruitId, RecruitRequest.RecruitUpdateDTO dto, Long corpId, SessionUser sessionUser) {
        log.info("공고 수정 요청 - recruitId: {}, corpId: {}, title: {}", recruitId, corpId, dto.getTitle());

        // 공고 조회 및 권한 확인
        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> new Exception404(RecruitErr.RECRUIT_NOT_FOUND.getMessage()));

        if (!recruit.getCorp().getCorpId().equals(sessionUser.getId()) || !recruit.getCorp().getCorpId().equals(corpId)) {
            throw new Exception403(RecruitErr.RECRUIT_FORBIDDEN.getMessage());
        }

        String oldImagePath = recruit.getImage();
        String savedFileName = oldImagePath;

        try {
            MultipartFile targetFile = null;

            // 1. DTO에 새 이미지(Base64)가 있는지 확인
            if (dto.getImage() != null && !dto.getImage().isBlank()) {
                targetFile = Base64FileConverterUtil.convert(dto.getImage());
            }

            // 2. 변환된 파일이 있으면 서버에 업로드
            if (targetFile != null) {
                savedFileName = fileUploadUtil.uploadProfileImage(targetFile, uploadPath.getRecruitDir());
            }

            // 3. 새 이미지 저장이 성공했고, 옛 이미지가 있었다면 삭제
            if (savedFileName != null && !savedFileName.equals(oldImagePath) && oldImagePath != null) {
                fileUploadUtil.deleteProfileImage(oldImagePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("이미지 처리 중 오류가 발생했습니다", e);
        }


        // DTO에서 Skill 목록 조회
        List<Skill> newSkills;
        if (dto.getSkillIds() != null && !dto.getSkillIds().isEmpty()) {
            newSkills = skillRepository.findAllById(dto.getSkillIds());
            if (newSkills.size() != dto.getSkillIds().size()) {
                throw new Exception404(SkillErr.SKILL_NOT_FOUND.getMessage());
            }
        } else {
            newSkills = Collections.emptyList();
        }

        // 엔티티에 업데이트 위임
        recruit.update(dto, savedFileName, newSkills);

        return RecruitResponse.RecruitListDTO.of(recruit);
    }

    // 공고 삭제
    @Transactional
    public void recruitDelete(Long recruitId, Long corpId, SessionUser sessionUser) {
        log.info("공고 삭제 요청 - recruitId: {}, corpId: {}", recruitId, corpId);

        Recruit recruit = recruitRepository.findById(recruitId)
                .orElseThrow(() -> new Exception404(RecruitErr.RECRUIT_NOT_FOUND.getMessage()));

        if (!recruit.getCorp().getCorpId().equals(sessionUser.getId()) || !recruit.getCorp().getCorpId().equals(corpId)) {
            throw new Exception403(RecruitErr.RECRUIT_FORBIDDEN.getMessage());
        }

        if (recruit.getImage() != null && !recruit.getImage().isBlank()) {
            fileUploadUtil.deleteProfileImage(recruit.getImage());
        }

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