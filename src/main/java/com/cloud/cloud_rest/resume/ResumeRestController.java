package com.cloud.cloud_rest.resume;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/resumes")
public class ResumeRestController {

    private final ResumeService resumeService;

    // 이력서 전체 조회
    @GetMapping
    public ResponseEntity<?> findAll() {
        List<ResumeResponse.ListDTO> resumes = resumeService.findAllResumeAndSkills();
        return ResponseEntity.ok().body(new ApiUtil<>(resumes));
    }

    // 이력서 상세보기
    @GetMapping("/{id}/detail")
    public ResponseEntity<ApiUtil<ResumeResponse.DetailDTO>> detail(
            @PathVariable(name = "id") Long resumeId,
            @RequestAttribute(value = "sessionUser", required = false)SessionUser sessionUser) {

        ResumeResponse.DetailDTO detailDTO = resumeService.detail(resumeId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(detailDTO));
    }

    // 이력서 작성
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody ResumeRequest.SaveDTO saveDTO,
                                  @RequestAttribute("sessionUser") SessionUser sessionUser) {
        ResumeResponse.SaveDTO savedResume = resumeService.save(saveDTO, sessionUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(savedResume));
    }

    // 이력서 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long resumeId,
                                    @Valid @RequestBody ResumeRequest.UpdateDTO updateDTO,
                                    @RequestAttribute("sessionUser")SessionUser sessionUser){

        ResumeResponse.UpdateDTO updateResume = resumeService.update(resumeId, updateDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(updateResume));
    }

    // 이력서 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiUtil<String>> delete(
            @PathVariable(name = "id") Long resumeId,
            @RequestAttribute("sessionUser") SessionUser sessionUser) {

        resumeService.deleteById(resumeId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>("이력서 삭제 성공"));
    }
}
