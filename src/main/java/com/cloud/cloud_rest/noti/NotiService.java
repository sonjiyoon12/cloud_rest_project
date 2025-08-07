package com.cloud.cloud_rest.noti;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpRepository;
import com.cloud.cloud_rest.recruit.Recruit;
import com.cloud.cloud_rest.recruit.RecruitRepository;
import com.cloud.cloud_rest.subcorp.SubCorpJpaRepository;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotiService {

    private final NotiJpaRepository notiJpaRepository;
    private final RecruitRepository recruitRepository;
    private final SubCorpJpaRepository subCorpJpaRepository;
    private final UserRepository userRepository;
    private final CorpRepository corpRepository;

    @Transactional
    public String save(Recruit recruit, String message) {
        recruitRepository.findById(recruit.getRecruitId())
                .orElseThrow(() -> new Exception404("존재하지 않는 공고입니다."));

        // 1. 회사의 존재 여부 확인
        Corp corp = corpRepository.findById(recruit.getCorp().getCorpId())
                .orElseThrow(() -> new Exception404("존재하지 않는 회사입니다."));

        // 2. subCorp 테이블에서 회사 아이디로 검색해서 유저 리스트 뽑아옴
        List<User> users = subCorpJpaRepository.findUserByCorpId(recruit.getCorp().getCorpId());

        // DTO 준비
        NotiRequest.SaveDTO notiRequest = new NotiRequest.SaveDTO();

        // 3. 실제 유저인지 확인 후, Noti 테이블에 저장
        if (!users.isEmpty()) {
            users.forEach(user -> {
                List<User> userList = userRepository.findByUserUserId(user.getUserId());

                if (!users.isEmpty()) {
                    for (int i = 0; i < users.size(); i++) {
                        Noti noti = notiRequest.toEntity(recruit, userList.get(i), message);
                        notiJpaRepository.save(noti);
                    }
                }
            });
            return "알림 전송 완료!";
        } else {
            return null;
        }
    }

    /**
     * 관리자가 만들어지면?
    public Page<NotiResponse.DetailDTO> findAll(SessionUser sessionUser, Pageable pageable) {
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception404("해당 유저를 찾을 수 없습니다."));

        if (user.getRole())
    }
     */

    public List<NotiResponse.DetailDTO> findAllByUserId(SessionUser sessionUser, Long userId, Pageable pageable) {
        User user = userRepository.findById(sessionUser.getId())
                .orElse(userRepository.findById(userId)
                        .orElseThrow(() -> new Exception404("존재하지 않는 유저입니다.")));

        if (!sessionUser.getId().equals(userId)) {
            throw new Exception403("접근 권한이 없습니다.");
        }

        Page<Noti> notis = notiJpaRepository.findByUserUserId(user.getUserId(), pageable);
        return notis.stream().map(NotiResponse.DetailDTO::new).toList();
    }
}
