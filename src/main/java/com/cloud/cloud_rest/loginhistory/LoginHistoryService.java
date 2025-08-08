package com.cloud.cloud_rest.loginhistory;

import com.cloud.cloud_rest.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LoginHistoryService {
    private final LoginHistoryRepository historyRepository;


    @Transactional
    public void save(User user,LoginHistoryRequest.SaveDTO saveDTO,String ipAddress,String userAgent){
        historyRepository.save(saveDTO.toEntity(user,ipAddress,userAgent));
    }
}
