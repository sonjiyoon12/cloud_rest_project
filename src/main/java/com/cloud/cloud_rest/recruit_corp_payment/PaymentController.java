package com.cloud.cloud_rest.recruit_corp_payment;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest.user.Role;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Payment : 기업용 유료결제(유료공고) API", description = "기업회원과 관리자에게 결제 관련 API를 제공합니다.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "[기업] 결제 생성 = 유료 공고 전환", description = "기업회원이 공고를 유료로 전환하기 위해 결제를 생성합니다.")
    @Auth(roles = {Role.CORP, Role.ADMIN})
    @PostMapping
    public ResponseEntity<PaymentResponse.PaymentSaveDTO> createPayment(
            @RequestBody @Valid PaymentRequest.PaymentSaveDTO requestDTO,
            @RequestAttribute SessionUser sessionUser) {
        PaymentResponse.PaymentSaveDTO responseDTO = paymentService.createPayment(requestDTO, sessionUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
