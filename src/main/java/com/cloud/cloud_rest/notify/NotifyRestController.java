package com.cloud.cloud_rest.notify;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifies")
@Tag(name = "Notify", description = "구독자 알림 관리 API")
public class NotifyRestController {

    private final NotifyService notifyService;

    // 모든 알림 불러오기(특정 사용자)
    @Operation(summary = "특정 사용자의 모든 알림 불러오기")
    @GetMapping("/{userId}/all")
    public ResponseEntity<?> findAllByUserId(@RequestAttribute("sessionUser")SessionUser sessionUser,
                                             @PathVariable("userId") Long userId,
                                             @PageableDefault(size = 10, page = 0) Pageable pageable) {

        List<NotifyResponse.DetailDTO> notis = notifyService.findAllByUserId(sessionUser, userId, pageable);
        return ResponseEntity.ok().body(new ApiUtil<>(notis));
    }

    // 모든 알림 읽기(특정 사용자)
    @Operation(summary = "특정 사용자의 모든 알림 읽기")
    @GetMapping("/{userId}/all/read")
    public ResponseEntity<?> readAllByUserId(@RequestAttribute("sessionUser")SessionUser sessionUser,
                                             @PathVariable("userId") Long userId) {
        notifyService.readAllByUserId(sessionUser, userId);
        return ResponseEntity.ok().build();
    }

    // 특정 알림 불러오기
    @Operation(summary = "특정 알림 불러오기")
    @GetMapping("/{notifyId}")
    public ResponseEntity<?> findByNotifyId(@RequestAttribute("sessionUser") SessionUser sessionUser,
                                          @PathVariable("notifyId") Long notifyId) {

        NotifyResponse.DetailDTO notify = notifyService.findByNotiId(sessionUser, notifyId);
        return ResponseEntity.ok().body(new ApiUtil<>(notify));
    }
}
