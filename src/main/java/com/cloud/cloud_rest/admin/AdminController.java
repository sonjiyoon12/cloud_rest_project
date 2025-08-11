package com.cloud.cloud_rest.admin;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest._global.exception.Exception401;
import com.cloud.cloud_rest.loginhistory.LoginHistoryResponse;
import com.cloud.cloud_rest.loginhistory.TodayResponse;
import com.cloud.cloud_rest.user.Role;
import com.cloud.cloud_rest.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/admin")
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/login")
    public ResponseEntity<?> adminLogin(@Valid @RequestBody AdminRequest.LoginDTO loginDTO){
        String jwtToken = adminService.login(loginDTO);

        User user = adminService.getLoginId(loginDTO.getLoginId()); // 아이디 있는지 체크

        AdminResponse.LoginDTO DTO = new AdminResponse.LoginDTO(user);
        return ResponseEntity.ok()
                .header("Authorization","Bearer " + jwtToken)
                .body(new ApiUtil<>(DTO));
    }

    @Auth(roles = Role.ADMIN)
    @PostMapping("/save")
    public ResponseEntity<?> adminSave(@Valid @RequestBody AdminRequest.SaveDTO saveDTO, @RequestAttribute("sessionUser") SessionUser sessionUser){

        if(!saveDTO.getPassword().equals(saveDTO.getRePassword())){
            throw new Exception401("비빌번호가 서로 다릅니다");
        }

        AdminResponse.SaveDTO save = adminService.save(saveDTO,sessionUser);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiUtil<>(save));
    }

    @Auth(roles = Role.ADMIN)
    @GetMapping("/{id}")
    public ResponseEntity<?> adminGetInfo(@PathVariable Long id,
                                          @RequestAttribute("sessionUser") SessionUser sessionUser){
        AdminResponse.UserDTO userDTO = adminService.findUserById(id,sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(userDTO));
    }

    // 모든 유저들의 정보
    @Auth(roles = Role.ADMIN)
    @GetMapping("/list")
    public ResponseEntity<?> adminList(@RequestAttribute("sessionUser") SessionUser sessionUser){
        List<AdminResponse.UserDTO> userDTOS = adminService.adminList(sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(userDTOS));
    }

    // 현재 로그인한 유저들의 목록
    @Auth(roles = Role.ADMIN)
    @GetMapping("/login-list")
    public ResponseEntity<?> loginUserList(@RequestAttribute("sessionUser") SessionUser sessionUser){
        List<LoginHistoryResponse.LoginDTO> loginHistory = adminService.findByLoginUserAll(sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(loginHistory));
    }
    
    // 오늘 로그인한 유저들의 목록
    @Auth(roles = Role.ADMIN)
    @GetMapping("/now/login-user")
    public ResponseEntity<?> nowLoginUserAll(@RequestAttribute("sessionUser") SessionUser sessionUser){
        TodayResponse.TodayLoginResponse loginHistory = adminService.nowLoginUserAll(sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(loginHistory));
    }

    // 오늘 회원가입 한 유저들의 목록
    @Auth(roles = Role.ADMIN)
    @GetMapping("/now/sign-user")
    public ResponseEntity<?> nowSignUserAll(@RequestAttribute("sessionUser") SessionUser sessionUser){
        TodayResponse.TodaySignResponse loginHistory = adminService.getTodaySignupUsers(sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(loginHistory));
    }
}
