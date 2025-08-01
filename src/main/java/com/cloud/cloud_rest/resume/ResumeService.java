package com.cloud.cloud_rest.resume;

import com.cloud.cloud_rest.errors.exception.Exception403;
import com.cloud.cloud_rest.errors.exception.Exception404;
import com.cloud.cloud_rest.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ResumeService {

    private final ResumeJpaRepository resumeJpaRepository;

    // 이력서 목록 조회
    public List<ResumeResponse.ListDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("resumeId").descending());
        Page<Resume> resumePage = resumeJpaRepository.findByAllUser(pageable);
        List<ResumeResponse.ListDTO> resumeList = new ArrayList<>();
        for(Resume resume : resumePage.getContent()) {
            ResumeResponse.ListDTO listDTO = new ResumeResponse.ListDTO(resume);
            resumeList.add(listDTO);
        }
        return resumeList;
    }

    // 이력서 상세보기
    public ResumeResponse.DetailDTO detail(Long resumeId, User user) {
        Resume resume = resumeJpaRepository.findByIdUser(resumeId).orElseThrow(
                () -> new Exception404("이력서를 찾을 수 없습니다"));

        return new ResumeResponse.DetailDTO(resume, user);
    }

    // 이력서 작성
    @Transactional
    public ResumeResponse.SaveDTO save(ResumeRequest.SaveDTO saveDTO, User user) {

//        // 세션유저에서 유저로 변환
//        User user = User.builder()
//                .id(sessionUser.getId())
//                .username(sessionUser.getUsername())
//                .email(sessionUser.getEmail())
//                .build();

        Resume resume = saveDTO.toEntity(user);
        Resume savedResume = resumeJpaRepository.save(resume);
        return new ResumeResponse.SaveDTO(savedResume);
    }

//    // 이력서 삭제
//    @Transactional
//    public void deleteById(Long resumeId, User Id) {
//        Resume resume = resumeJpaRepository.findById(resumeId).orElseThrow(() ->
//                new Exception404("삭제하려는 이력서가 없습니다"));
//
//        if(!resume.isOwner(id.getId())){
//            throw new Exception403("본인이 작성한 게시글만 삭제할 수 있습니다");
//        }
//        resumeJpaRepository.deleteById(resumeId);
//    }
//
//    // 게시글 수정
//    @Transactional
//    public ResumeResponse.UpdateDTO update(Long resumeId, ResumeRequest.UpdateDTO updateDTO,
//                                           SessionUser sessionUser) {
//        Resume resume = resumeJpaRepository.findById(resumeId).orElseThrow(() ->
//                new Exception404("해당 이력서가 존재하지 않습니다"));
//
//        if(!resume.isOwner(sessionUser.getId())) {
//            throw new Exception403("본인이 작성한 이력서만 수정할 수 있습니다");
//        }
//        resume.update(updateDTO);
//        return new ResumeResponse.UpdateDTO(resume);
//    }







}
