package com.cloud.cloud_rest.admin;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception400;
import com.cloud.cloud_rest._global.exception.Exception401;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest._global.utils.JwtUtil;
import com.cloud.cloud_rest.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public AdminResponse.SaveDTO save(AdminRequest.SaveDTO saveDTO){
        String encodePassword = passwordEncoder.encode(saveDTO.getPassword());

        if(userRepository.existsLoginId(saveDTO.getLoginId())){
            throw new Exception400("이미 사용 중인 아이디입니다.");
        }

        User admin = saveDTO.toEntity(encodePassword, Role.ADMIN);
        userRepository.save(admin);
        return new AdminResponse.SaveDTO(admin);
    }

    @Transactional
    public String login(AdminRequest.LoginDTO loginDTO){
        User user = userService.getLoginId(loginDTO.getLoginId());
        
        if(!passwordEncoder.matches(loginDTO.getPassword(),user.getPassword())){
            throw new Exception401("서로 비밀번호가 일치하지 않습니다");
        }
        
        if(user.getRole() != Role.ADMIN){
            throw  new Exception403("관리자 유저가 아닙니다");
        }

        return JwtUtil.createToken(user);
    }

    // 유저의 상세 정보
    public AdminResponse.UserDTO findUserById(Long userId, SessionUser sessionUser) {

        if (sessionUser.getRole() != Role.ADMIN) {
            throw new Exception403("관리자 유저만 접근 가능합니다.");
        }

        User user = userService.getUserId(userId);
        return new AdminResponse.UserDTO(user);
    }

    // 유저들의 모든 정보 보기
    public List<AdminResponse.UserDTO> adminList(SessionUser sessionUser) {

        if (sessionUser.getRole() != Role.ADMIN) {
            throw new Exception403("관리자 유저만 접근 가능합니다.");
        }
        List<User> users = userRepository.findAllByRole(Role.USER);
        return users.stream()
                .map(AdminResponse.UserDTO::new)
                .collect(Collectors.toList());
    }


    public User getLoginId(String loginId){
        return userRepository.findByUserId(loginId).orElseThrow(() -> new Exception404("해당 유저가 존재하지 않습니다"));
    }
}
