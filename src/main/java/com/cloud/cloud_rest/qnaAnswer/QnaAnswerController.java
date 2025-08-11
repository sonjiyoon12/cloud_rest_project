package com.cloud.cloud_rest.qnaAnswer;

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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/qna/{qnaBoardId}/qna-answers")
@Tag(name = "QnaAnswer", description = "문의 답변 관리 API")
public class QnaAnswerController {

    private final QnaAnswerService qnaAnswerService;

    // 답변 작성
    @Auth(roles = {Role.ADMIN})
    @PostMapping
    @Operation(summary = "답변 작성하기")
    public ResponseEntity<?> save(@PathVariable(name = "qnaBoardId") Long qnaBoardId,
                                  @Valid @RequestBody QnaAnswerRequest.SaveDTO saveDTO){
        QnaAnswerResponse.QnaAnswerResponseDTO savedQnaAnswer = qnaAnswerService.save(qnaBoardId, saveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(savedQnaAnswer));

    }

    // 답변 수정
    @Auth(roles = {Role.ADMIN})
    @PutMapping("/{id}")
    @Operation(summary = "답변 수정하기")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long qnaAnswerId,
                                    @Valid @RequestBody QnaAnswerRequest.UpdateDTO updateDTO) {
        QnaAnswerResponse.QnaAnswerResponseDTO updateQnaAnswer = qnaAnswerService.update(qnaAnswerId, updateDTO);
        return ResponseEntity.ok(new ApiUtil<>(updateQnaAnswer));
    }

    // 답변 삭제
    @Auth(roles = {Role.ADMIN})
    @DeleteMapping("/{id}")
    @Operation(summary = "답변 삭제하기")
    public ResponseEntity<ApiUtil<String>> delete(@PathVariable(name = "id") Long qnaAnswerId){
        qnaAnswerService.deleteById(qnaAnswerId);
        return ResponseEntity.ok(new ApiUtil<>("답변 삭제 성공"));
    }

}
