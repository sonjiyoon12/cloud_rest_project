package com.cloud.cloud_rest.subcorp;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/subcorps")
public class SubCorpRestController {

    private final SubCorpService subCorpService;

    // 구독기능
    @PostMapping
    public ResponseEntity<?> subscribe(SubCorpRequest.SaveDTO saveDTO,
                                       @RequestAttribute("sessionUser")SessionUser sessionUser) {

        SubCorpResponse.SaveDTO savedSubCorp = subCorpService.saveSubCorp(saveDTO, sessionUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSubCorp);
    }

    // 구독 목록 조회
    @GetMapping("/{userId}")
    public ResponseEntity<?> findAll(@PathVariable("userId") Long userId,
                                     @RequestAttribute("sessionUser") SessionUser sessionUser) {
        List<SubCorpResponse.DetailDTO> subList = subCorpService.findAll(userId, sessionUser);
        return ResponseEntity.ok().body(new ApiUtil<>(subList));
    }

    // 구독취소
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id,
                                    @RequestAttribute("sessionUser") SessionUser sessionUser) {
        subCorpService.delete(id, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>("삭제 성공!"));
    }
}
