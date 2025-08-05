package com.cloud.cloud_rest.user;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.exception.Exception401;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest.corp.CorpRequest;
import com.cloud.cloud_rest.corp.CorpResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    // 유저 회원가입
    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody UserRequest.SaveDTO saveDTO){

        if(!saveDTO.getPassword().equals(saveDTO.getRePassword())){
            throw new Exception401("비빌번호가 서로 다릅니다");
        }

        UserResponse.SaveDTO save = userService.save(saveDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiUtil<>(save));
    }

    // 유저 로그인 API
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequest.LoginDTO loginDTO){
        String jwtToken = userService.login(loginDTO);
        User login = userService.getLoginId(loginDTO.getLoginId());
        UserResponse.LoginDTO DTO = new UserResponse.LoginDTO(login);
        return ResponseEntity.ok()
                .header("Authorization","Bearer " + jwtToken)
                .body(new ApiUtil<>(DTO));
    }

    // 유저 회원 정보 가져오기
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable(name = "id")Long id,
                                         @RequestAttribute("sessionUser") SessionUser sessionUser){

        if(!sessionUser.getRole().equals("USER")){
            throw new Exception403("일반 유저만 볼수있습니다");
        }

        UserResponse.UserDTO userDTO = userService.findUserById(id,sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(userDTO));
    }


    // 웹 MULTIPART 형식 으로 받기
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @ModelAttribute UserRequest.UpdateDTO updateDTO,
                                    @RequestAttribute("sessionUser") SessionUser sessionUser) {
        UserResponse.UpdateDTO update = userService.update(id,sessionUser.getId(),updateDTO);
        return ResponseEntity.ok(new ApiUtil<>(update));
    }

    // 웹에서 JSON 형식으로 받기(base64)
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateJson(@PathVariable Long id,
                                        @RequestBody UserRequest.UpdateDTO updateDTO,
                                        @RequestAttribute("sessionUser") SessionUser sessionUser) {
        UserResponse.UpdateDTO update = userService.update(id,sessionUser.getId(),updateDTO);
        return ResponseEntity.ok(new ApiUtil<>(update));
    }

    // 임시로 로그아웃 (사실상 필요없음) 서버에서 JWT를 강제로 무효화하려면 블랙리스트 같은 추가 설계 필요
    @GetMapping("/logout")
    public ResponseEntity<SessionUser> logout(@RequestAttribute("sessionUser") SessionUser sessionUser) {
        return ResponseEntity.ok(sessionUser);
    }

}
