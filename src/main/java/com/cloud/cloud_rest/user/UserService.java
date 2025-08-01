package com.cloud.cloud_rest.user;

import com.cloud.cloud_rest.errors.exception.Exception403;
import com.cloud.cloud_rest.errors.exception.Exception404;
import com.cloud.cloud_rest.errors.exception.Exception500;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    // 회원가입
    @Transactional
    public UserResponse.SaveDTO save(UserRequest.SaveDTO saveDTO) {
        String bcyPassword = bCryptPasswordEncoder.encode(saveDTO.getPassword());
        User user = saveDTO.toEntity(bcyPassword);
        userRepository.save(user);
        return new UserResponse.SaveDTO(user);
    }

    // 로그인 
    public String login(UserRequest.LoginDTO loginDTO) {
        String bcyPassword = bCryptPasswordEncoder.encode(loginDTO.getPassword());
        User user = userRepository.findByUserId(loginDTO.getLoginId())
                .orElseThrow(() -> new Exception404("회원 유저를 찾을 수 없습니다"));

        if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new Exception500("아이디 또는 비밀번호 틀립니다");
        }

        String jwt = JwtUtil.create(user);
        return jwt;
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
    public UserResponse.UpdateDTO update(Long userId,Long sessionUserId,UserRequest.UpdateDTO updateDTO,String userUploadImage){
        validateUserUserId(userId,sessionUserId);
        User user = getUserId(userId);
        user.update(updateDTO,userUploadImage);
        return new UserResponse.UpdateDTO(user,userUploadImage);
    }

    // 유저 정보 찾기(user에 고유번호)
    public User getUserId(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new Exception404("해당 유저를 찾을수없습니다"));
    }

    // 권한 검사 (요청 로그인 번호 - 로그인 번호 ) 비교
    public void validateUserUserId(Long userId,Long sessionUserId){
        if(!userId.equals(sessionUserId)){
          throw new Exception403("보인 정보만 조회 가능합니다");
        }
    }

}
