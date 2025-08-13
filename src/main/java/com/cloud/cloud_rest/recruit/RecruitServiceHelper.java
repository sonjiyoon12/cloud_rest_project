package com.cloud.cloud_rest.recruit;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest._global.exception.Exception500;
import com.cloud.cloud_rest._global.utils.Base64FileConverterUtil;
import com.cloud.cloud_rest._global.utils.FileUploadUtil;
import com.cloud.cloud_rest._global.utils.UploadProperties;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpRepository;
import com.cloud.cloud_rest.skill.Skill;
import com.cloud.cloud_rest.skill.SkillErr;
import com.cloud.cloud_rest.skill.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RecruitServiceHelper {

    private final FileUploadUtil fileUploadUtil;
    private final UploadProperties uploadProperties;
    private final CorpRepository corpRepository;
    private final SkillRepository skillRepository;
    private final RecruitRepository recruitRepository; // 추가: 채용공고 레포지토리

    // 공고 등록 권한 확인
    public void checkSavePermission(Long corpId, SessionUser sessionUser) {
        if (!corpId.equals(sessionUser.getId())) {
            throw new Exception403(RecruitErr.RECRUIT_FORBIDDEN.getMessage());
        }
    }

    // 공고 수정/삭제 권한 확인
    public void checkUpdateOrDeletePermission(Recruit recruit, Long corpId, SessionUser sessionUser) {
        if (!recruit.getCorp().getCorpId().equals(sessionUser.getId()) || !recruit.getCorp().getCorpId().equals(corpId)) {
            throw new Exception403(RecruitErr.RECRUIT_FORBIDDEN.getMessage());
        }
    }

    // ID로 기업을 찾아 반환 (없으면 404 예외 발생)
    public Corp findCorpById(Long corpId) {
        return corpRepository.findById(corpId)
                .orElseThrow(() -> new Exception404(RecruitErr.CORP_NOT_FOUND.getMessage()));
    }

    // ID로 채용공고를 찾아 반환 (없으면 404 예외 발생)
    public Recruit findRecruitById(Long recruitId) {
        return recruitRepository.findById(recruitId)
                .orElseThrow(() -> new Exception404(RecruitErr.RECRUIT_NOT_FOUND.getMessage()));
    }

    // 스킬 ID 목록의 유효성 검증 및 Skill 엔티티 목록 반환
    public List<Skill> validateSkillIds(List<Long> skillIds) {
        if (skillIds == null || skillIds.isEmpty()) {
            return List.of(); // 빈 리스트 반환 (스킬이 없을 수도 있으므로)
        }
        List<Skill> skills = skillRepository.findAllById(skillIds);
        if (skills.size() != skillIds.size()) {
            throw new Exception404(SkillErr.SKILL_NOT_FOUND.getMessage());
        }
        return skills;
    }

    // Base64 이미지 저장 및 파일 이름 반환
    public String saveImageFromBase64(String base64Image) {
        if (base64Image == null || base64Image.isBlank()) {
            return null;
        }
        try {
            MultipartFile targetFile = Base64FileConverterUtil.convert(base64Image);
            return fileUploadUtil.uploadProfileImage(targetFile, uploadProperties.getRecruitDir());
        } catch (IOException e) {
            throw new Exception500(RecruitErr.IMAGE_PROCESS_FAILED.getMessage());
        }
    }

    // 새 이미지 저장 및 기존 이미지 삭제 후 파일 이름 반환
    public String updateImageFromBase64(String newBase64Image, String oldImagePath) {
        // 새 이미지가 없는 경우, 기존 이미지 경로를 그대로 반환
        if (newBase64Image == null || newBase64Image.isBlank()) {
            return oldImagePath;
        }

        try {
            // 1. Base64 이미지를 파일로 변환
            MultipartFile targetFile = Base64FileConverterUtil.convert(newBase64Image);

            // 2. 새 파일 업로드
            String newImagePath = fileUploadUtil.uploadProfileImage(targetFile, uploadProperties.getRecruitDir());

            // 3. 새 이미지 저장이 성공했고, 옛 이미지가 있었다면 삭제
            if (newImagePath != null && !newImagePath.equals(oldImagePath) && oldImagePath != null) {
                fileUploadUtil.deleteProfileImage(oldImagePath);
            }
            return newImagePath;

        } catch (IOException e) {
            throw new Exception500(RecruitErr.IMAGE_PROCESS_FAILED.getMessage());
        }
    }

    // 이미지 파일 삭제
    public void deleteImage(String imagePath) {
        if (imagePath != null && !imagePath.isBlank()) {
            fileUploadUtil.deleteProfileImage(imagePath);
        }
    }
}
