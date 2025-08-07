package com.cloud.cloud_rest._global;

import com.cloud.cloud_rest.user.Role;
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
    private Role role;
    private String images;

}
