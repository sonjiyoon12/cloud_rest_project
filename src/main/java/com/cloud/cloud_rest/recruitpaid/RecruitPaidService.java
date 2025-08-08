package com.cloud.cloud_rest.recruitpaid;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception400;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest.recruit.Recruit;
import com.cloud.cloud_rest.recruit.RecruitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RecruitPaidService {
    private final RecruitPaidRepository recruitPaidRepository;
    private final RecruitRepository recruitRepository;

    // 기업용 유료공고 변환
    @Transactional
    public RecruitPaidResponse.PaidSaveDTO paidSave(
            RecruitPaidRequest.PaidSaveDTO requestDTO,
            SessionUser sessionUser) {
        // 1. 공고 조회
        Recruit recruit = recruitRepository.findById(requestDTO.getRecruitId())
                .orElseThrow(() -> new Exception404(RecruitPaidErr.NOT_FOUND.getMessage()));

        // 2. 공고 소유권 확인
        if (recruit.getCorp().getCorpId() != sessionUser.getId()) {
            throw new Exception403(RecruitPaidErr.FORBIDDEN.getMessage());
        }

        // 3. 이미 유료 공고인지 확인 (PK로 확인)
        if (recruitPaidRepository.existsById(requestDTO.getRecruitId())) {
            throw new Exception400(RecruitPaidErr.BAD_REQUEST.getMessage());
        }

        // 4. 유료 공고 엔티티 생성 및 저장 (정적 팩토리 메소드 사용)
        RecruitPaid recruitPaid = RecruitPaid.create(recruit, requestDTO.getDurationInDays());
        recruitPaidRepository.save(recruitPaid);

        // 5. 응답 DTO 변환 후 반환
        return new RecruitPaidResponse.PaidSaveDTO(recruitPaid);
    }

    // 유료공고 상세보기
    public RecruitPaidResponse.PaidDetailDTO paidDetail(Long recruitId) {
        return recruitPaidRepository.findById(recruitId)
                .map(RecruitPaidResponse.PaidDetailDTO::new) // 찾으면, 유료 상태 DTO 생성
                .orElse(new RecruitPaidResponse.PaidDetailDTO(recruitId)); // 못 찾으면, 무료 상태 DTO 생성
    }

    // 관리자용 유료공고 목록 조회
    public List<RecruitPaidResponse.PaidListDTO> paidList() {
        List<RecruitPaid> paidRecruits = recruitPaidRepository.findAll();

        return paidRecruits.stream()
                .map(RecruitPaidResponse.PaidListDTO::new)
                .collect(Collectors.toList());
    }

    // 관리자용 유료공고 삭제
    @Transactional
    public void paidDelete(Long recruitId) {
        RecruitPaid recruitPaid = recruitPaidRepository.findById(recruitId)
                .orElseThrow(() -> new Exception404(RecruitPaidErr.NOT_FOUND.getMessage()));
        recruitPaidRepository.delete(recruitPaid);
    }
}
