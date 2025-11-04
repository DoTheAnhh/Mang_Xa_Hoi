package com.example.mang_xa_hoi.dto.user.response;

import com.example.mang_xa_hoi.dto.user_role.response.UserRoleResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Long id;

    private String code;

    private String name;

    private List<UserRoleResponse> userRoles;
}
