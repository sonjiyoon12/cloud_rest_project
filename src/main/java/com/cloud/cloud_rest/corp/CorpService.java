package com.cloud.cloud_rest.corp;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception400;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest._global.utils.*;
import com.cloud.cloud_rest.skill.Skill;
import com.cloud.cloud_rest.skill.SkillRepository;
import com.cloud.cloud_rest.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorpService {
    private final CorpRepository corpRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FileUploadUtil fileUploadUtil; // 이미지 저장 및 삭제 기능
    private final UploadProperties uploadPath; // 이미지 저장 경로 설정
    private final SkillRepository skillRepository;

    @Transactional
    public CorpResponse.CorpDTO save(CorpRequest.SaveDTO saveDTO) {
        String bcyPassword = bCryptPasswordEncoder.encode(saveDTO.getPassword());

        if (corpRepository.existsLoginId(saveDTO.getLoginId())) {
            throw new Exception400("이미 사용 중인 아이디입니다.");
        }

        Corp corp = saveDTO.toEntity(bcyPassword);

        if (!saveDTO.getCorpSkills().isEmpty()) {
            for (Long skillId : saveDTO.getCorpSkills()) {
                Skill skill = skillRepository.findById(skillId)
                        .orElseThrow(() -> new Exception400("존재하지 않는 스킬입니다"));
                corp.addSkill(skill);
            }
        }

        corpRepository.save(corp);
        return new CorpResponse.CorpDTO(corp);
    }

    // 로그인 API 
    public String login(CorpRequest.LoginDTO loginDTO) {

        Corp corp = getLoginId(loginDTO.getLoginId());

        if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), corp.getPassword())) {
            throw new Exception400("비밀번호가 일치 하지 않습니다");
        }

        if(corp.getRole() != Role.CORP){
            throw  new Exception403("기업 유저가 아닙니다");
        }

        corp.validateApproval(); // 승인된 기업만 정보 수정 가능

        return JwtUtil.createToken(corp);
    }

    // 수정 API
    @Transactional
    public CorpResponse.UpdateDTO updateDTO(Long id, CorpRequest.UpdateDTO dto,SessionUser sessionUser) {
        Corp corp = getCorpId(id); // 해당 유저가 있는지
        corp.validateApproval(); // 승인된 기업만 정보 수정 가능
        AuthorizationUtil.validateCorpAccess(id, sessionUser); // 어드민 유저 및 본인 권한 검사

        String oldImagePath = corp.getCorpImage();
        String savedFileName = null;

        try {
            MultipartFile targetFile = null;

            // 웹(Multipart) 용
            if (dto.getCorpImage() != null && !dto.getCorpImage().isEmpty()) {
                targetFile = dto.getCorpImage();
            }
            // 앱(Base64) 용
            else if (dto.getCorpImageBase64() != null && !dto.getCorpImageBase64().isBlank()) {
                targetFile = Base64FileConverterUtil.convert(dto.getCorpImageBase64());
            }

            // 해당 파일이 없으면 savedFileName에 이름저장
            if (targetFile != null) {
                savedFileName = fileUploadUtil.uploadProfileImage(targetFile, uploadPath.getCorpDir());
            }

            // 이전 이미지 삭제
            if (savedFileName != null && oldImagePath != null) {
                fileUploadUtil.deleteProfileImage(oldImagePath);
            }

            // 해당 파일을 저장
            corp.update(dto, savedFileName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new CorpResponse.UpdateDTO(corp);
    }

    @Transactional
    public void deleteById(Long id, SessionUser sessionUser) {

        AuthorizationUtil.validateCorpAccess(id, sessionUser); // 어드민 유저 및 본인 권한 검사
        Corp corp = getCorpId(id);
        corp.validateApproval(); // 승인된 기업만 정보 수정 가능

        corpRepository.delete(corp);
    }

    public CorpResponse.CorpDTO getCorpInfo(Long id, SessionUser sessionUser) {

        Corp corp = getCorpId(id);

        AuthorizationUtil.validateCorpAccess(id,sessionUser);

        return new CorpResponse.CorpDTO(corp);
    }

    // Corp Login ID로 해당 유저 찾기
    public Corp getLoginId(String loginId) {
        return corpRepository.findByLoginId(loginId)
                .orElseThrow(() -> new Exception404("해당 유저를 찾을 수 없습니다"));
    }

    // Corp Id로 해당 유저 찾기
    public Corp getCorpId(Long id) {
        return corpRepository.findById(id)
                .orElseThrow(() -> new Exception404("해당 유저를 찾을 수 없습니다"));
    }




}
