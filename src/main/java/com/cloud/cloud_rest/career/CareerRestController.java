package com.cloud.cloud_rest.career;


import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest.resume.Resume;
import com.cloud.cloud_rest.resume.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/careers")
@Tag(name = "Career", description = "경력 사항 관리를 위한 API")
public class CareerRestController {

    private final CareerService careerService;
    private final ResumeService resumeService;

    // 경력 작성
    @PostMapping("/resume/{id}")
    @Operation(summary = "경력 사항 작성하기")
    public ResponseEntity<?> save(@Valid @RequestBody CareerRequest.SaveDTO saveDTO,
                                  @PathVariable(name = "id") Long resumeId,
                                  @RequestAttribute("sessionUser")SessionUser sessionUser) {

        Resume resume = resumeService.findById(resumeId);
        CareerResponse.InfoDTO savedCareer = careerService.save( resumeId, saveDTO, sessionUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(savedCareer));
    }

    // 경력 수정
    @PutMapping("/{id}")
    @Operation(summary = "경력 사항 수정하기")
    public ResponseEntity<?> update (@Valid @RequestBody CareerRequest.UpdateDTO updateDTO,
                                     @PathVariable(name = "id") Long careerId,
                                     @RequestAttribute("sessionUser") SessionUser sessionUser) {

        CareerResponse.InfoDTO updatedCareer = careerService.update(careerId, updateDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(updatedCareer));
    }

    // 경력 삭제
    @DeleteMapping("/{id}")
    @Operation(summary = "경력 사항 삭제하기")
    public ResponseEntity<ApiUtil<String>> delete(@PathVariable(name = "id") Long careerId,
                                                  @RequestAttribute("sessionUser") SessionUser sessionUser) {
        careerService.delete(careerId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>("경력 삭제 성공"));
    }
}