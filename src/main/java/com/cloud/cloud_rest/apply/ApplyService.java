package com.cloud.cloud_rest.apply;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest.recruit.Recruit;
import com.cloud.cloud_rest.recruit.RecruitRepository;
import com.cloud.cloud_rest.resume.Resume;
import com.cloud.cloud_rest.resume.ResumeJpaRepository;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplyService {

    private final ApplyJpaRepository applyJpaRepository;
    private final ResumeJpaRepository resumeJpaRepository;
    private final RecruitRepository recruitJpaRepository;
    private final UserRepository userRepository;

    // 공고 지원
    @Transactional
    public ApplyResponse.SaveDTO save(ApplyRequest.SaveDTO saveDTO) {
        Resume resume = resumeJpaRepository.findById(saveDTO.getResumeId())
                .orElseThrow(() -> new Exception404("이력서를 찾을 수 없습니다."));

        Recruit recruit = recruitJpaRepository.findById(saveDTO.getRecruitId())
                .orElseThrow(() -> new Exception404("공고글을 찾을 수 없습니다."));

        ApplyRequest.SaveDTO saveDTOs = new ApplyRequest.SaveDTO();
        Apply apply = saveDTOs.toEntity(resume, recruit);
        applyJpaRepository.save(apply);

        return new ApplyResponse.SaveDTO(apply);
    }

    // 전체 공고 지원 내역 조회
    public List<ApplyResponse.DetailDTO> findAll() {
        List<Apply> applies = applyJpaRepository.findAll();

        return applies.stream()
                .map(ApplyResponse.DetailDTO::new)
                .toList();
    }

    // 특정 공고 지원 내역 조회
    public ApplyResponse.DetailDTO findById(Long applyId, SessionUser sessionUser) {
        Apply apply = applyJpaRepository.findById(applyId)
                .orElseThrow(() -> new Exception404("공고 지원 내역을 찾을 수 없습니다."));

        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("해당 유저를 찾을 수 없습니다."));

        if (!user.getUserId().equals(apply.getResume().getUser().getUserId())) {
            throw new Exception403("본인 지원 내역만 볼 수 있습니다.");
        }

        return new ApplyResponse.DetailDTO(apply);
    }

    // 특정 공고 지원 내역 삭제
    @Transactional
    public void deleteById(Long id) {
        Apply apply = applyJpaRepository.findById(id)
                .orElseThrow(() -> new Exception404("게시글을 찾을 수 없습니다."));

        applyJpaRepository.delete(apply);
    }
}
