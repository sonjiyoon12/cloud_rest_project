package com.cloud.cloud_rest.recruit_corp_payment;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest.recruit.Recruit;
import com.cloud.cloud_rest.recruit.RecruitErr;
import com.cloud.cloud_rest.recruit.RecruitRepository;
import com.cloud.cloud_rest.recruit_paid.RecruitPaidRequest;
import com.cloud.cloud_rest.recruit_paid.RecruitPaidService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RecruitRepository recruitRepository;
    private final RecruitPaidService recruitPaidService;

    @Transactional
    public PaymentResponse.PaymentSaveDTO createPayment(PaymentRequest.PaymentSaveDTO requestDTO, SessionUser sessionUser) {
        log.info("결제 생성 요청 - recruitId: {}, duration: {}", requestDTO.getRecruitId(), requestDTO.getDuration());

        // 1. 채용공고 조회
        Recruit recruit = recruitRepository.findById(requestDTO.getRecruitId())
                .orElseThrow(() -> new Exception404(RecruitErr.RECRUIT_NOT_FOUND.getMessage()));

        // 2. 결제 기록 생성 및 저장
        Payment payment = Payment.builder()
                .recruit(recruit)
                .duration(requestDTO.getDuration())
                .build();
        paymentRepository.save(payment);

        // 3. RecruitPaidService 호출하여 유료 공고 생성/업데이트
        RecruitPaidRequest.PaidSaveDTO paidSaveDTO = new RecruitPaidRequest.PaidSaveDTO();
        paidSaveDTO.setRecruitId(requestDTO.getRecruitId());
        paidSaveDTO.setDurationInDays(requestDTO.getDuration().getDays());
        recruitPaidService.paidSave(paidSaveDTO, sessionUser);

        // 4. 응답 DTO 생성 및 반환
        return PaymentResponse.PaymentSaveDTO.builder()
                .paymentId(payment.getPaymentId())
                .recruitId(recruit.getRecruitId())
                .duration(payment.getDuration())
                .build();
    }
}
