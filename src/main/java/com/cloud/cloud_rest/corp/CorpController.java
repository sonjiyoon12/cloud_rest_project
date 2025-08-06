package com.cloud.cloud_rest.corp;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest._global.exception.Exception401;
import com.cloud.cloud_rest._global.exception.Exception403;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/corp")
public class CorpController {

    private final CorpService corpService;


    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody CorpRequest.SaveDTO saveDTO){

        if(!saveDTO.getPassword().equals(saveDTO.getRePassword())){
            throw new Exception401("비빌번호가 서로 다릅니다");
        }

        CorpResponse.CorpDTO corpDTO = corpService.save(saveDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiUtil<>(corpDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody CorpRequest.LoginDTO loginDTO){
        String jwtToken = corpService.login(loginDTO);
        Corp corp = corpService.getLoginId(loginDTO.getLoginId());

        CorpResponse.LoginDTO login = new CorpResponse.LoginDTO(corp);

        return ResponseEntity.ok()
                .header("Authorization","Bearer " + jwtToken)
                .body(new ApiUtil<>(login));
    }

    // 기업 정보 알아보기
    @Auth
    @GetMapping("/{id}")
    public ResponseEntity<?> getCropInfo(@PathVariable(name = "id")Long id,
                                           @RequestAttribute("sessionUser") SessionUser sessionUser){
        CorpResponse.CorpDTO corp = corpService.getCorpInfo(id,sessionUser);


        return ResponseEntity.ok(new ApiUtil<>(corp));
    }

    // 웹 MULTIPART 형식 으로 받기
    @Auth
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> update(@PathVariable Long id,
                                    @ModelAttribute CorpRequest.UpdateDTO updateDTO,
                                    @RequestAttribute("sessionUser")SessionUser sessionUser) {
        CorpResponse.UpdateDTO update = corpService.updateDTO(id, updateDTO,sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(update));
    }

    // 웹에서 JSON 형식으로 받기(base64)
    @Auth
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateJson(@PathVariable Long id,
                                        @RequestBody CorpRequest.UpdateDTO updateDTO,
                                        @RequestAttribute("sessionUser")SessionUser sessionUser) {
        CorpResponse.UpdateDTO update = corpService.updateDTO(id, updateDTO,sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(update));
    }

    @Auth
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id,
                           @RequestAttribute("sessionUser")SessionUser sessionUser){
        corpService.deleteById(id,sessionUser);
        return ResponseEntity.noContent().build();
    }

    // 임시로 로그아웃 (사실상 필요없음)
    @GetMapping("/logout")
    public ResponseEntity<SessionUser> logout(@RequestAttribute("sessionUser") SessionUser sessionUser) {

        return ResponseEntity.ok(sessionUser);
    }
}
