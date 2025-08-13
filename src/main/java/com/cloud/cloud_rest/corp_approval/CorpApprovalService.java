package com.cloud.cloud_rest.corp_approval;

import com.cloud.cloud_rest._global.SessionUser;
import com.cloud.cloud_rest._global.utils.AuthorizationUtil;
import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.corp.CorpService;
import com.cloud.cloud_rest.user.User;
import com.cloud.cloud_rest.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CorpApprovalService {

    private final CorpApprovalRepository corpApprovalRepository;
    private final CorpService corpService;
    private final UserService userService;

    @Transactional
    public CorpApprovalResponse.ApprovalDTO userApproval(Long id,SessionUser sessionUser,CorpApprovalRequest.ApprovalDTO corpApprovalRequest){

        AuthorizationUtil.validateAdminAccess(sessionUser); // 관리자인지 체크하기

        Corp corp = corpService.getCorpId(id); // 기업이 있는지 없는지 유무 검사
        User adminUser = userService.getUserId(sessionUser.getId());
        CorpApproval corpApproval = corpApprovalRepository.save(corpApprovalRequest.toEntity(corp,adminUser));
        corp.setCorpStatus(corpApprovalRequest.getCorpStatus());
        return new CorpApprovalResponse.ApprovalDTO(corpApproval);
    }
}
