package com.cloud.cloud_rest.recruit_corp_payment;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.auth.Auth;
import com.cloud.cloud_rest.user.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 생성 (유료 공고 전환)
    @Auth(roles = {Role.CORP, Role.ADMIN})
    @PostMapping
    public ResponseEntity<PaymentResponse.PaymentSaveDTO> createPayment(
            @RequestBody @Valid PaymentRequest.PaymentSaveDTO requestDTO,
            @RequestAttribute SessionUser sessionUser) {
        PaymentResponse.PaymentSaveDTO responseDTO = paymentService.createPayment(requestDTO, sessionUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
