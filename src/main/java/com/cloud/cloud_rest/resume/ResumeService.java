package com.cloud.cloud_rest.resume;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest._global.utils.Base64FileConverterUtil;
import com.cloud.cloud_rest._global.utils.FileUploadUtil;
import com.cloud.cloud_rest._global.utils.UploadProperties;
import com.cloud.cloud_rest.career.Career;
import com.cloud.cloud_rest.career.CareerRequest;
import com.cloud.cloud_rest.resumeskill.ResumeSkill;
import com.cloud.cloud_rest.skill.Skill;
import com.cloud.cloud_rest.skill.SkillRepository;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ResumeService {

    private final ResumeJpaRepository resumeJpaRepository;
    private final UserService userService;
    private final SkillRepository skillRepository;
    private final FileUploadUtil fileUploadUtil; // 이미지 저장 및 삭제 기능
    private final UploadProperties uploadPath; // 이미지 저장 경로 설정

    @Autowired
    private EntityManager entityManager;

    // 이력서 전체 조회
    public List<ResumeResponse.ListDTO> findAllResumes() {
        List<Resume> resumes = resumeJpaRepository.findAllResumeAndSkillsAndCareers();

        return resumes.stream()
                .map(ResumeResponse.ListDTO::new)
                .toList();
    }

    // 이력서 단건 조회
    public Resume findById(Long resumeId) {
        return resumeJpaRepository.findById(resumeId)
                .orElseThrow(() -> new Exception404("이력서를 찾을 수 없습니다"));
    }

    // 이력서 상세보기
    public ResumeResponse.DetailDTO detail(Long resumeId, SessionUser sessionUser) {
        Resume resume = resumeJpaRepository.findByIdWithDetail(resumeId).orElseThrow(
                () -> new Exception404("이력서를 찾을 수 없습니다"));

        return new ResumeResponse.DetailDTO(resume, sessionUser);
    }

    // 이력서 작성
    @Transactional
    public ResumeResponse.SaveDTO save(ResumeRequest.SaveDTO saveDTO, SessionUser sessionUser) {
        User user = userService.getUserId(sessionUser.getId());

        String savedFileName = null;
        try {
            MultipartFile targetFile = Base64FileConverterUtil.convert(saveDTO.getImage());
            savedFileName = fileUploadUtil.uploadProfileImage(targetFile,uploadPath.getResumeDir());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Resume resume = saveDTO.toEntity(user, savedFileName);

        for (Long skillId : saveDTO.getSkillIds()) {
            Skill skill = skillRepository.findById(skillId)
                    .orElseThrow(() -> new Exception404("스킬을 찾을 수 없습니다"));

            ResumeSkill resumeSkill = new ResumeSkill(resume, skill);
            resume.addResumeSkill(resumeSkill);
        }

        // 경력 추가
        for (CareerRequest.SaveDTO careerSaveDTO : saveDTO.getCareers()) {
            Career career = careerSaveDTO.toEntity(resume);
            resume.addCareer(career);
        }

        Resume savedResume = resumeJpaRepository.save(resume);
            return new ResumeResponse.SaveDTO(savedResume);
    }

    // 이력서 수정
    @Transactional
    public ResumeResponse.UpdateDTO update(Long resumeId, ResumeRequest.UpdateDTO updateDTO,
                                           SessionUser sessionUser) {
        Resume resume = resumeJpaRepository.findById(resumeId)
                .orElseThrow(() -> new Exception404("해당 이력서가 존재하지 않습니다"));

        if (!resume.isOwner(sessionUser.getId())) {
            throw new Exception403("본인이 작성한 이력서만 수정할 수 있습니다");
        }

        String oldImagePath = resume.getImage();
        String savedFileName = null;

        try {
            if (updateDTO.getImage() != null && !updateDTO.getImage().isBlank()) {
                MultipartFile targetFile = Base64FileConverterUtil.convert(updateDTO.getImage());
                savedFileName = fileUploadUtil.uploadProfileImage(targetFile, uploadPath.getResumeDir());

                if (savedFileName != null && oldImagePath != null) {
                    fileUploadUtil.deleteProfileImage(oldImagePath);
                }
            } else {
                savedFileName = oldImagePath; // 기존 이미지 유지
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 1 기본 정보 수정
        resume.update(updateDTO, savedFileName);

        // 2 스킬 교체 (기존 제거 후 새로 추가)
        resume.getResumeSkills().clear();
        entityManager.flush();
        for (Long skillId : updateDTO.getSkillIds()) {
            Skill skill = skillRepository.findById(skillId)
                    .orElseThrow(() -> new Exception404("스킬을 찾을 수 없습니다"));
            ResumeSkill resumeSkill = new ResumeSkill(resume, skill);
            resume.addResumeSkill(resumeSkill);
        }

        // 3 경력 개별 수정 처리
        Map<Long, Career> existingCareerMap = new HashMap<>();
        for (Career career : resume.getCareers()) {
            existingCareerMap.put(career.getCareerId(), career);
        }

        List<Career> updatedCareers = new ArrayList<>();

        for (CareerRequest.UpdateDTO dto : updateDTO.getCareers()) {
            if (dto.getCareerId() == null) {
                // 신규 경력
                Career newCareer = Career.builder()
                        .corpName(dto.getCorpName())
                        .position(dto.getPosition())
                        .content(dto.getContent())
                        .startAt(dto.getStartAt())
                        .endAt(dto.getEndAt())
                        .resume(resume)
                        .build();
                updatedCareers.add(newCareer);
            } else {
                // 기존 경력 수정
                Career existing = existingCareerMap.get(dto.getCareerId());
                if (existing == null) {
                    throw new Exception404("수정할 경력이 존재하지 않습니다");
                }
                existing.update(dto);
                updatedCareers.add(existing);
                existingCareerMap.remove(dto.getCareerId());
            }
        }

        // 4 삭제할 경력 제거 (existingCareerMap에 남은 것들)
        for (Career toDelete : existingCareerMap.values()) {
            resume.getCareers().remove(toDelete);
        }

        // 5 경력 리스트 교체
        for (Career career : updatedCareers) {
            if (career.getCareerId() == null) {
                resume.getCareers().add(career); // resume에 연결된 새로운 Career만 추가
            }
        }

        return new ResumeResponse.UpdateDTO(resume);
    }

    // 이력서 삭제
    @Transactional
    public void deleteById(Long resumeId, SessionUser sessionUser) {
        Resume resume = resumeJpaRepository.findById(resumeId).orElseThrow(() ->
                new Exception404("삭제하려는 이력서가 없습니다"));

        if(!resume.isOwner(sessionUser.getId())){
            throw new Exception403("본인이 작성한 게시글만 삭제할 수 있습니다");
        }

        resumeJpaRepository.deleteById(resumeId);
    }
}
