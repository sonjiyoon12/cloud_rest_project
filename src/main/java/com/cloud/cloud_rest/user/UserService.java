package com.cloud.cloud_rest.user;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.*;
import com.cloud.cloud_rest._global.utils.*;
import com.cloud.cloud_rest.loginhistory.LoginHistoryRequest;
import com.cloud.cloud_rest.loginhistory.LoginHistoryService;
import com.cloud.cloud_rest.skill.Skill;
import com.cloud.cloud_rest.skill.SkillRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FileUploadUtil fileUploadUtil; // 이미지 저장 및 삭제 기능
    private final UploadProperties uploadPath; // 이미지 저장 경로 설정
    private final SkillRepository skillRepository;
    private final LoginHistoryService loginHistoryService;

    // 회원가입
    @Transactional
    public UserResponse.SaveDTO save(UserRequest.SaveDTO saveDTO) {
        String bcyPassword = bCryptPasswordEncoder.encode(saveDTO.getPassword());

        if (userRepository.existsLoginId(saveDTO.getLoginId())) {
            throw new Exception400("이미 사용 중인 아이디입니다.");
        }

        User user = saveDTO.toEntity(bcyPassword);


        if (!saveDTO.getUserSkills().isEmpty()) {
            for (Long skillId : saveDTO.getUserSkills()) {
                Skill skill = skillRepository.findById(skillId)
                        .orElseThrow(() -> new Exception400("존재하지 않는 스킬입니다."));
                user.addSkill(skill);
            }
        }
        userRepository.save(user);
        return new UserResponse.SaveDTO(user);
    }

    // 로그인
    @Transactional
    public String login(UserRequest.LoginDTO loginDTO, HttpServletRequest request) {
        User user = getLoginId(loginDTO.getLoginId());

        if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new Exception401("아이디 또는 비밀번호 틀립니다");
        }

        if (user.getRole() != Role.USER && user.getRole() != Role.ADMIN) {
            throw new Exception403("로그인 권한이 없습니다.");
        } // jwt 인증 토큰이 아님 (회원가입 Session 여부 토큰임)


        loginHistoryService.deactivateAllActiveUserLogins(user.getUserId());

        String ipAddress = HttpRequestUtil.getClientIp(request);
        String userAgent = HttpRequestUtil.getUserAgent(request);

        LoginHistoryRequest.SaveUserDTO saveUserDTO = new LoginHistoryRequest.SaveUserDTO();
        loginHistoryService.saveUser(user, saveUserDTO, ipAddress, userAgent);

        return JwtUtil.createToken(user);
    }

    // 유저의 상세 정보
    public UserResponse.UserDTO findUserById(Long userId, SessionUser sessionUser) {

        User user = getUserId(userId); // 유저가 있는 체크

        AuthorizationUtil.validateUserAccess(userId, sessionUser); // 수정에 관한 권한 여부

        return new UserResponse.UserDTO(user);
    }

    // 유저의 정보 수정
    @Transactional
    public UserResponse.UpdateDTO update(Long userId, SessionUser sessionUser, UserRequest.UpdateDTO updateDTO) {

        User user = getUserId(userId); // 현재 유저가 있을경우

        AuthorizationUtil.validateUserAccess(userId, sessionUser); // 수정에 관한 권한 여부


        String oldImagePath = user.getUserImage();
        String savedFileName = null;

        try {
            MultipartFile targetFile = null;
            // 웹용
            if (updateDTO.getUserImage() != null
                    && !(updateDTO.getUserImage().isEmpty())) {
                targetFile = updateDTO.getUserImage();
            }
            // 앱(base64) 용
            else if (updateDTO.getUserImageBase64() != null && !updateDTO.getUserImageBase64().isBlank()) {
                targetFile = Base64FileConverterUtil.convert(updateDTO.getUserImageBase64());
            }


            if (targetFile != null) {
                savedFileName = fileUploadUtil.uploadProfileImage(targetFile, uploadPath.getUserDir());
            }

            // 이전 이미지 삭제
            if (savedFileName != null && oldImagePath != null) {
                fileUploadUtil.deleteProfileImage(oldImagePath);
            }

            if (updateDTO.getUserSkillIds() != null && !updateDTO.getUserSkillIds().isEmpty()) {
                List<Skill> skills = skillRepository.findAllById(updateDTO.getUserSkillIds());
                user.updateSkills(skills);
            }


            user.update(updateDTO, savedFileName);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new UserResponse.UpdateDTO(user);
    }

    // 유저 정보 삭제
    @Transactional
    public void deleteById(Long id, SessionUser sessionUser) {

        User user = getUserId(id); // 유저 정보 찾기
        AuthorizationUtil.validateUserAccess(id, sessionUser);

        userRepository.delete(user);
    }

    // 유저 정보 찾기(user에 고유번호)
    public User getUserId(Long loginId) {
        return userRepository.findById(loginId).orElseThrow(() -> new Exception404("해당 유저를 찾을수없습니다"));
    }

    // 유저 정보 찾기(user에 고유번호)
    public User getLoginId(String loginId) {
        return userRepository.findByUserId(loginId).orElseThrow(() -> new Exception404("해당 유저를 찾을수없습니다"));
    }

}
