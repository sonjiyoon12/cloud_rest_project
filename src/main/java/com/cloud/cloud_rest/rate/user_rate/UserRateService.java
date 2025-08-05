package com.cloud.cloud_rest.rate.user_rate;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.exception.Exception400;
import com.cloud.cloud_rest._global.exception.Exception403;
import com.cloud.cloud_rest._global.exception.Exception404;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpRepository;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRateService {

    private final UserRateJpaRepository userRateJpaRepository;
    private final CorpRepository corpRepository;
    private final UserRepository userRepository;

    @Transactional
    public UserRateResponse.SaveDTO save(UserRateRequest.SaveDTO saveDTO, SessionUser sessionUser, Long corpId) {
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception403("평점을 남길 수 있는 권한이 없습니다."));

        Corp corp = corpRepository.findById(corpId)
                .orElseThrow(() -> new Exception404("존재하지 않는 회사입니다."));

        UserRate userRate = userRateJpaRepository.findByUserUserIdAndCorpCorpId(user.getUserId(), corp.getCorpId());

        if (userRate != null) {
            throw new Exception400("이미 평점을 남긴 회사입니다.");
        }

        userRate = saveDTO.toEntity(user, corp);
        userRateJpaRepository.save(userRate);
        return new UserRateResponse.SaveDTO(userRate);
    }

    public List<UserRateResponse.DetailDTO> findAll() {
        List<UserRate> userRates = userRateJpaRepository.findAll();
        return userRates.stream()
                .map(UserRateResponse.DetailDTO::new)
                .toList();
    }

    public List<UserRateResponse.DetailDTO> findByCorpId(Long corpId) {
        List<UserRate> userRates = userRateJpaRepository.findByCorpCorpId(corpId)
                .orElseThrow(() -> new Exception404("해당 회사에 평점이 없습니다."));
        return userRates.stream()
                .map(UserRateResponse.DetailDTO::new)
                .toList();
    }

    @Transactional
    public void deleteById(Long userRateId, SessionUser sessionUser) {
        User user = userRepository.findById(sessionUser.getId())
                .orElseThrow(() -> new Exception403("평점을 남길 수 있는 권한이 없습니다."));

        UserRate userRate = userRateJpaRepository.findById(userRateId)
                .orElseThrow(() -> new Exception404("존재하지 않는 평점입니다."));

        if (!user.getUserId().equals(userRate.getUser().getUserId())) {
            throw new Exception403("본인만 삭제 가능합니다.");
        }

        userRateJpaRepository.deleteById(userRateId);
    }
}
