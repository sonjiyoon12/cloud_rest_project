package com.cloud.cloud_rest.career;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest.resume.Resume;
import com.cloud.cloud_rest.resume.ResumeJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CareerService {

    private final CareerJpaRepository careerJpaRepository;
    private final ResumeJpaRepository resumeJpaRepository;

    // 경력 저장
    @Transactional
    public CareerResponse.InfoDTO save(Long resumeId, CareerRequest.SaveDTO saveDTO, SessionUser sessionUser) {

        Resume resume = resumeJpaRepository.findById(resumeId)
                .orElseThrow(() -> new Exception404("이력서를 찾을 수 없습니다"));


        if (!resume.isOwner(sessionUser.getId())) {
            throw new Exception403("본인이 작성한 이력서만 작성할 수 있습니다");
        }

        Career career = saveDTO.toEntity(resume);
        Career saved = careerJpaRepository.save(career);
        return new CareerResponse.InfoDTO(saved);
    }


    // 경력 수정
    @Transactional
    public CareerResponse.InfoDTO update(Long careerId, CareerRequest.UpdateDTO updateDTO,
                                         SessionUser sessionUser) {
        Career career = careerJpaRepository.findById(careerId)
                .orElseThrow(() -> new Exception404("해당 경력을 찾을 수 없습니다"));

        if (!career.getResume().isOwner(sessionUser.getId())) {
            throw new Exception403("본인 경력만 수정할 수 있습니다");
        }

        career.update(updateDTO);
        return new CareerResponse.InfoDTO(career);
    }

    // 경력 삭제
    @Transactional
    public void delete(Long careerId, SessionUser sessionUser){
        Career career = careerJpaRepository.findById(careerId)
                .orElseThrow(() -> new Exception404("해당 경력을 찾을 수 없습니다"));

        if (!career.getResume().isOwner(sessionUser.getId())) {
            throw new Exception403("본인 경력만 삭제할 수 있습니다");
        }
        careerJpaRepository.deleteById(careerId);
    }
}