package com.cloud.cloud_rest.career;


import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global._core.common.ApiUtil;
import com.cloud.cloud_rest.resume.Resume;
import com.cloud.cloud_rest.resume.ResumeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/careers")
public class CareerRestController {

    private final CareerService careerService;
    private final ResumeService resumeService;

    // 경력 작성
    @PostMapping("/resume/{id}")
    public ResponseEntity<?> save(@Valid @RequestBody CareerRequest.CareerSaveDTO saveDTO,
                                  @PathVariable(name = "id") Long resumeId,
                                  @RequestAttribute("sessionUser")SessionUser sessionUser) {

        Resume resume = resumeService.findById(resumeId);
        CareerResponse.InfoDTO savedCareer = careerService.save( resumeId, saveDTO, sessionUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(savedCareer));
    }

    // 경력 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> update (@Valid @RequestBody CareerRequest.CareerUpdateDTO updateDTO,
                                     @PathVariable(name = "id") Long careerId,
                                     @RequestAttribute("sessionUser") SessionUser sessionUser) {

        CareerResponse.InfoDTO updatedCareer = careerService.update(careerId, updateDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(updatedCareer));
    }

    // 경력 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiUtil<String>> delete(@PathVariable(name = "id") Long careerId,
                                                  @RequestAttribute("sessionUser") SessionUser sessionUser) {
        careerService.delete(careerId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>("경력 삭제 성공"));
    }
}