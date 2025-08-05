package com.cloud.cloud_rest.user;

import com.cloud.cloud_rest._global.exception.Exception400;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest._global.exception.Exception500;
import com.cloud.cloud_rest._global.utils.Base64FileConverterUtil;
import com.cloud.cloud_rest._global.utils.FileUploadUtil;
import com.cloud.cloud_rest._global.utils.JwtUtil;
import com.cloud.cloud_rest._global.utils.UploadProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final FileUploadUtil fileUploadUtil; // 이미지 저장 및 삭제 기능
    private final UploadProperties uploadPath; // 이미지 저장 경로 설정
    private final Base64FileConverterUtil base64FileConverterUtil; // json 으로 받는 파일 String64 변환

    // 회원가입
    @Transactional
    public UserResponse.SaveDTO save(UserRequest.SaveDTO saveDTO) {
        String bcyPassword = bCryptPasswordEncoder.encode(saveDTO.getPassword());

        if(userRepository.existsLoginId(saveDTO.getLoginId())){
            throw new Exception400("이미 사용 중인 아이디입니다.");
        }

        User user = saveDTO.toEntity(bcyPassword);
        userRepository.save(user);
        return new UserResponse.SaveDTO(user);
    }

    // 로그인 
    public String login(UserRequest.LoginDTO loginDTO) {
        User user = getLoginId(loginDTO.getLoginId());

        if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(),user.getPassword())) {
            throw new Exception500("아이디 또는 비밀번호 틀립니다");
        }

        return JwtUtil.createForUser(user);
    }

    // 유저의 상세 정보
    public UserResponse.UserDTO findUserById(Long userId,Long sessionUserId) {

        if(!userId.equals(sessionUserId)){
            throw new Exception403("보인 정보만 조회 가능합니다");
        }

        User user = getUserId(userId);

        return new UserResponse.UserDTO(user);
    }

    // 유저의 정보 수정
    @Transactional
    public UserResponse.UpdateDTO update(Long userId,Long sessionUserId,UserRequest.UpdateDTO updateDTO){
        validateUserUserId(userId,sessionUserId); // 현재 로그인한 유저랑 세션 번호를 비교
        User user = getUserId(userId); // 현재 유저가 있을경우

        String oldImagePath = user.getUserImage();
        String savedFileName = null;

        try{
            MultipartFile targetFile = null;
            // 웹용
            if (updateDTO.getUserImage() != null
                    && !(updateDTO.getUserImage().isEmpty())) {
                targetFile = updateDTO.getUserImage();
            }
            // 앱(base64) 용
            else if (updateDTO.getUserImageBase64() != null && !updateDTO.getUserImageBase64().isBlank()){
                targetFile = Base64FileConverterUtil.convert(updateDTO.getUserImageBase64());
            }


            if(targetFile != null){
                savedFileName = fileUploadUtil.uploadProfileImage(targetFile,uploadPath.getUploadDir());
            }
            
            // 이전 이미지 삭제
            if(savedFileName != null && oldImagePath != null){
                fileUploadUtil.deleteProfileImage(oldImagePath);
            }


            user.update(updateDTO,savedFileName);

        }catch (IOException e){
            throw new RuntimeException(e);
        }

        return new UserResponse.UpdateDTO(user);
    }

    // 유저 정보 찾기(user에 고유번호)
    public User getUserId(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new Exception404("해당 유저를 찾을수없습니다"));
    }

    // 유저 정보 찾기(user에 고유번호)
    public User getLoginId(String loginId){
        return userRepository.findByUserId(loginId).orElseThrow(() -> new Exception404("해당 유저를 찾을수없습니다"));
    }

    // 권한 검사 (요청 로그인 번호 - 로그인 번호 ) 비교
    public void validateUserUserId(Long userId,Long sessionUserId){
        if(!userId.equals(sessionUserId)){
          throw new Exception403("보인 정보만 조회 가능합니다");
        }
    }

}
