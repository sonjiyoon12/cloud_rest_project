package com.cloud.cloud_rest.rate.corp_rate;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/corps/rate")
@RequiredArgsConstructor
public class CorpRateRestController {

    private final CorpRateService corpRateService;

    // 평점 남기기
    @PostMapping
    public ResponseEntity<?> save(CorpRateRequest.SaveDTO saveDTO,
                                  @RequestAttribute("sessionUser") SessionUser sessionUser,
                                  @RequestParam("userId") Long userId) {

        CorpRateResponse.SaveDTO savedRate = corpRateService.save(saveDTO, sessionUser, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(savedRate));
    }

    // 모든 평점 조회
    @GetMapping("/all")
    public ResponseEntity<?> findAll() {
        List<CorpRateResponse.DetailDTO> detailRates = corpRateService.findAll();
        return ResponseEntity.ok().body(new ApiUtil<>(detailRates));
    }

    // 특정 유저의 평점 조회
    @GetMapping
    public ResponseEntity<?> findByUserId(@RequestParam("userId") Long userId) {
        List<CorpRateResponse.DetailDTO> detailRates = corpRateService.findByUserId(userId);
        return ResponseEntity.ok().body(new ApiUtil<>(detailRates));
    }

    // 평점 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable(name = "id") Long corpRateId,
                                        @RequestAttribute("sessionUser") SessionUser sessionUser) {
        corpRateService.deleteById(corpRateId, sessionUser);
        return ResponseEntity.ok().body("삭제 성공!");
    }
}
