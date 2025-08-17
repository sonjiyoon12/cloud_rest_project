package com.cloud.cloud_rest.subcorp;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subcorps")
@Tag(name = "SubCorp", description = "기업 구독 관리 API")
public class SubCorpRestController {

    private final SubCorpService subCorpService;

    // 구독기능
    @Operation(summary = "기업 구독 기능")
    @PostMapping
    public ResponseEntity<?> subscribe(@RequestBody SubCorpRequest.SaveDTO saveDTO,
                                       @RequestAttribute("sessionUser")SessionUser sessionUser) {

        SubCorpResponse.SaveDTO savedSubCorp = subCorpService.saveSubCorp(saveDTO, sessionUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubCorp);
    }

    // 구독 목록 조회
    @Operation(summary = "기업 구독 목록 조회 기능")
    @GetMapping("/{userId}/")
    public ResponseEntity<?> findAll(@PathVariable("userId") Long userId,
                                     @RequestAttribute("sessionUser") SessionUser sessionUser) {
        List<SubCorpResponse.DetailDTO> subList = subCorpService.findAll(userId, sessionUser);
        return ResponseEntity.ok().body(new ApiUtil<>(subList));
    }

    // 구독취소
    @Operation(summary = "기업 구독 취소 기능")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                    @RequestAttribute("sessionUser") SessionUser sessionUser) {
        subCorpService.delete(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>("삭제 성공!"));
    }
}
