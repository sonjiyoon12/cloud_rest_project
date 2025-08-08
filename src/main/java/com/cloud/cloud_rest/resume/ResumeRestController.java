package com.cloud.cloud_rest.resume;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest.user.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/resumes")
@Tag(name = "Resume", description = "이력서 관리를 위한 API")
public class ResumeRestController {

    private final ResumeService resumeService;

    // 이력서 전체 조회
    @Auth(roles = {Role.ADMIN, Role.USER})
    @GetMapping
    @Operation(summary = "이력서 전체 조회")
    public ResponseEntity<?> findAll() {
        List<ResumeResponse.ListDTO> resumes = resumeService.findAllResumes();
        return ResponseEntity.ok().body(new ApiUtil<>(resumes));
    }

    // 이력서 상세보기
    @Auth(roles = {Role.ADMIN, Role.USER})
    @GetMapping("/{id}/detail")
    @Operation(summary = "특정 이력서 상세보기")
    public ResponseEntity<ApiUtil<ResumeResponse.DetailDTO>> detail(
            @PathVariable(name = "id") Long resumeId,
            @RequestAttribute(value = "sessionUser", required = false)SessionUser sessionUser) {

        ResumeResponse.DetailDTO detailDTO = resumeService.detail(resumeId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(detailDTO));
    }

    // 이력서 작성
    @Auth(roles = {Role.ADMIN, Role.USER})
    @PostMapping
    @Operation(summary = "이력서 작성하기")
    public ResponseEntity<?> save(@Valid @RequestBody ResumeRequest.SaveDTO saveDTO,
                                  @RequestAttribute("sessionUser") SessionUser sessionUser) {
        ResumeResponse.SaveDTO savedResume = resumeService.save(saveDTO, sessionUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(savedResume));
    }

    // 이력서 수정
    @Auth(roles = {Role.ADMIN, Role.USER})
    @PutMapping("/{id}")
    @Operation(summary = "이력서 수정하기")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long resumeId,
                                    @Valid @RequestBody ResumeRequest.UpdateDTO updateDTO,
                                    @RequestAttribute("sessionUser")SessionUser sessionUser){

        ResumeResponse.UpdateDTO updateResume = resumeService.update(resumeId, updateDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(updateResume));
    }

    // 이력서 삭제
    @Auth(roles = {Role.ADMIN, Role.USER})
    @DeleteMapping("/{id}")
    @Operation(summary = "이력서 삭제하기")
    public ResponseEntity<ApiUtil<String>> delete(
            @PathVariable(name = "id") Long resumeId,
            @RequestAttribute("sessionUser") SessionUser sessionUser) {

        resumeService.deleteById(resumeId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>("이력서 삭제 성공"));
    }
}
