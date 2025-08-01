package com.cloud.cloud_rest.user;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.exception.Exception403;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;


    @PostMapping("/")
    public ResponseEntity<?> save(@Valid @RequestBody UserRequest.SaveDTO saveDTO){
        UserResponse.SaveDTO save = userService.save(saveDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiUtil<>(save));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserRequest.LoginDTO loginDTO){
        String jwtToken = userService.login(loginDTO);
        return ResponseEntity.ok()
                .header("Authorization","Bearer" + jwtToken)
                .body(new ApiUtil<>(null));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfo(@PathVariable(name = "id")Long id,
                                         @RequestAttribute("sessionUser") SessionUser sessionUser){

        if(!sessionUser.getRole().equals("USER")){
            throw new Exception403("일반 유저만 볼수있습니다");
        }

        UserResponse.UserDTO userDTO = userService.findUserById(id,sessionUser.getId());
        return ResponseEntity.ok(new ApiUtil<>(userDTO));
    }



}
