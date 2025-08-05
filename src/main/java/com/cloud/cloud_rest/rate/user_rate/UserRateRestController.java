package com.cloud.cloud_rest.rate.user_rate;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/rate")
@RequiredArgsConstructor
public class UserRateRestController {

    private final UserRateService userRateService;

    // 평점 남기기
    @PostMapping
    public ResponseEntity<?> save(UserRateRequest.SaveDTO saveDTO,
                                  @RequestAttribute("sessionUser")SessionUser sessionUser,
                                  @RequestParam("corpId") Long corpId) {

        UserRateResponse.SaveDTO savedRate = userRateService.save(saveDTO, sessionUser, corpId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(savedRate));
    }

    // 모든 평점 조회
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        List<UserRateResponse.DetailDTO> detailRates = userRateService.findAll();
        return ResponseEntity.ok().body(new ApiUtil<>(detailRates));
    }

    // 특정 회사의 평점 조회
    @GetMapping
    public ResponseEntity<?> findByCorpId(@RequestParam("corpId") Long corpId) {
        List<UserRateResponse.DetailDTO> detailRates = userRateService.findByCorpId(corpId);
        return ResponseEntity.ok().body(new ApiUtil<>(detailRates));
    }

    // 평점 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long userRateId,
                                        @RequestAttribute("sessionUser") SessionUser sessionUser) {
        userRateService.deleteById(userRateId, sessionUser);
        return ResponseEntity.ok().body("삭제 성공!");
    }
}
