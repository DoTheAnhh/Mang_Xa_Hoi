package com.example.mang_xa_hoi.dto.user.request;

import com.example.mang_xa_hoi.dto.user_role.request.UserRoleRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {

    private String username;

    private String code;

    private String name;

    private List<UserRoleRequest> userRoles;
}
