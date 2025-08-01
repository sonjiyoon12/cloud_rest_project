package com.cloud.cloud_rest._global;

import com.cloud.cloud_rest.corp.Corp;
import com.cloud.cloud_rest.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SessionUser {
    private Long id;
    private String userId;
    private String username;
    private String role;
    private String images;


    public static SessionUser fromMember(User user) {
        return new SessionUser(user.getUserId(),user.getLoginId(),user.getUsername(),"USER",user.getUserImage());
    }

    public static SessionUser fromCorp(Corp corp) {
        return new SessionUser(corp.getCorpId(), corp.getLoginId(),corp.getCorpName() , "CORP",corp.getCorpImage());
    }
}
