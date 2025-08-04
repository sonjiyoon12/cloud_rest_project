package com.cloud.cloud_rest.resume;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ResumeService {

    private final ResumeJpaRepository resumeJpaRepository;
    private final UserRepository userRepository;

    // 이력서 전체 조회
    public List<ResumeResponse.ListDTO> findAll() {
        List<Resume> resumes = resumeJpaRepository.findAll();

        return resumes.stream()
                .map(ResumeResponse.ListDTO::new)
                .toList();
    }

    // 이력서 상세보기
    public ResumeResponse.DetailDTO detail(Long resumeId, SessionUser sessionUser) {
        Resume resume = resumeJpaRepository.findByIdUser(resumeId).orElseThrow(
                () -> new Exception404("이력서를 찾을 수 없습니다"));

        return new ResumeResponse.DetailDTO(resume, sessionUser);
    }

    // 이력서 작성
    @Transactional
    public ResumeResponse.SaveDTO save(ResumeRequest.SaveDTO saveDTO, SessionUser sessionUser) {
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
                () -> new Exception404("유저를 찾을 수 없습니다"));

        Resume resume = saveDTO.toEntity(user);
        Resume savedResume = resumeJpaRepository.save(resume);
        return new ResumeResponse.SaveDTO(savedResume);
    }

    // 이력서 삭제
    @Transactional
    public void deleteById(Long resumeId, SessionUser sessionUser) {
        Resume resume = resumeJpaRepository.findById(resumeId).orElseThrow(() ->
                new Exception404("삭제하려는 이력서가 없습니다"));

        if(!resume.isOwner(sessionUser.getId())){
            throw new Exception403("본인이 작성한 게시글만 삭제할 수 있습니다");
        }
        resumeJpaRepository.deleteById(resumeId);
    }

    // 이력서 수정
    @Transactional
    public ResumeResponse.UpdateDTO update(Long resumeId, ResumeRequest.UpdateDTO updateDTO,
                                           SessionUser sessionUser) {
        Resume resume = resumeJpaRepository.findById(resumeId).orElseThrow(() ->
                new Exception404("해당 이력서가 존재하지 않습니다"));

        if(!resume.isOwner(sessionUser.getId())) {
            throw new Exception403("본인이 작성한 이력서만 수정할 수 있습니다");
        }
        resume.update(updateDTO);
        return new ResumeResponse.UpdateDTO(resume);
    }
}
