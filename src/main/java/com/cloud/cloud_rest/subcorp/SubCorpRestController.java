package com.cloud.cloud_rest.subcorp;

import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest.apply.ApplyResponse;
import com.cloud.cloud_rest.corp.Corp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubCorpRestController {

    private SubCorpService subCorpService;


    // 구독기능




    // 구독취소


    // 구독 목록 조회
    @GetMapping("/subcorps/{userid}")
    public ResponseEntity<ApiUtil<List<SubCorpResponse.SubCorpResponseDTO>>> List(@PathVariable Long userId) {
        //List<Corp> corpList = subCorpService.getSubscribedCorps(userId);
        List<SubCorpResponse.SubCorpResponseDTO> applies = subCorpService.getSubscribedCorps(userId);
        return ResponseEntity.ok().body(new ApiUtil<>(applies));
    }






}
