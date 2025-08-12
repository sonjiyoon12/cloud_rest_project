package com.cloud.cloud_rest.qnaBoard;

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
@RequestMapping("/api/qna-boards")
@Tag(name = "QnaBoard", description = "문의 내역 관리 API")
public class QnaBoardRestController {

    private final QnaBoardService qnaBoardService;

    // 문의 전체 조회
    @Auth(roles = {Role.ADMIN, Role.USER})
    @GetMapping
    @Operation(summary = "문의 전체 조회")
    public ResponseEntity<?> findAll() {
        List<QnaBoardResponse.ListDTO> QnaBoards = qnaBoardService.findAllQna();
        return ResponseEntity.ok().body(new ApiUtil<>(QnaBoards));
    }

    // 문의 상세보기
    @Auth(roles = {Role.ADMIN, Role.USER})
    @GetMapping("/{id}/detail")
    @Operation(summary = "특정 문의 상세보기")
    public ResponseEntity<ApiUtil<QnaBoardResponse.DetailDTO>> detail(
            @PathVariable(name = "id") Long qnaBoardId,
            @RequestAttribute(value = "sessionUser", required = false)SessionUser sessionUser){

        QnaBoardResponse.DetailDTO detailDTO = qnaBoardService.detail(qnaBoardId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(detailDTO));
    }

    // 문의 작성
    @Auth(roles = {Role.ADMIN, Role.USER})
    @PostMapping
    @Operation(summary = "문의 작성하기")
    public ResponseEntity<?> save(@Valid @RequestBody QnaBoardRequest.SaveDTO saveDTO,
                                  @RequestAttribute("sessionUser") SessionUser sessionUser) {
        QnaBoardResponse.QnaBoardResponseDTO savedQnaBoard = qnaBoardService.save(saveDTO, sessionUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiUtil<>(savedQnaBoard));
    }

    // 문의 수정
    @Auth(roles = {Role.ADMIN, Role.USER})
    @PutMapping("/{id}")
    @Operation(summary = "문의 수정하기")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long qnaBoardId,
                                    @Valid @RequestBody QnaBoardRequest.UpdateDTO updateDTO,
                                    @RequestAttribute("sessionUser") SessionUser sessionUser){
        QnaBoardResponse.QnaBoardResponseDTO updateQnaBoard = qnaBoardService.update(qnaBoardId, updateDTO, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>(updateQnaBoard));
    }

    // 문의 삭제
    @Auth(roles = {Role.ADMIN, Role.USER})
    @DeleteMapping("/{id}")
    @Operation(summary = "문의 삭제하기")
    public ResponseEntity<ApiUtil<String>> delete(@PathVariable(name = "id") Long qnaBoardId,
                                                  @RequestAttribute("sessionUser") SessionUser sessionUser){
        qnaBoardService.deleteById(qnaBoardId, sessionUser);
        return ResponseEntity.ok(new ApiUtil<>("문의 삭제 성공"));
    }
}
