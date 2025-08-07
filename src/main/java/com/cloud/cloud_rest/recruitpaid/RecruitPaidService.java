package com.cloud.cloud_rest.recruitpaid;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest.recruit.Recruit;
import com.cloud.cloud_rest.recruit.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RecruitPaidService {
    private final RecruitPaidRepository recruitPaidRepository;
    private final RecruitRepository recruitRepository;

    @Transactional
    public RecruitPaidResponse.PaidSaveDTO paidSave(
            RecruitPaidRequest.PaidSaveDTO requestDTO,
            SessionUser sessionUser) {
        // 1. 공고 조회
        Recruit recruit = recruitRepository.findById(requestDTO.getRecruitId())
                .orElseThrow(() -> new RuntimeException("공고를 찾을 수 없습니다."));

        // 2. 공고 소유권 확인
        if (recruit.getCorp().getCorpId() != sessionUser.getId()) {
            throw new RuntimeException("자신의 공고만 유료로 전환할 수 있습니다.");
        }

        // 3. 이미 유료 공고인지 확인 (PK로 확인)
        if (recruitPaidRepository.existsById(requestDTO.getRecruitId())) {
            throw new RuntimeException("이미 유료로 등록된 공고입니다.");
        }

        // 4. 만료일 계산
        LocalDate expiryDate = LocalDate.now().plusDays(requestDTO.getDurationInDays());

        // 5. 유료 공고 엔티티 생성 및 저장
        RecruitPaid recruitPaid = RecruitPaid.builder()
                .recruit(recruit)
                .expiryDate(expiryDate)
                .build();
        recruitPaidRepository.save(recruitPaid);

        // 6. 응답 DTO 변환 후 반환
        return new RecruitPaidResponse.PaidSaveDTO(recruitPaid);
    }

    public RecruitPaidResponse.PaidDetailDTO paidDetail(Long recruitId) {
        // PK로 유료 공고를 조회합니다.
        return recruitPaidRepository.findById(recruitId)
                .map(RecruitPaidResponse.PaidDetailDTO::new) // 찾으면, 유료 상태 DTO 생성
                .orElse(new RecruitPaidResponse.PaidDetailDTO(recruitId)); // 못 찾으면, 무료 상태 DTO 생성
    }
}