package com.example.mang_xa_hoi.service.impl;

import com.example.mang_xa_hoi.common.SearchParams;
import com.example.mang_xa_hoi.dto.user.request.UserRequest;
import com.example.mang_xa_hoi.dto.user.response.UserResponse;
import com.example.mang_xa_hoi.dto.user_role.request.UserRoleRequest;
import com.example.mang_xa_hoi.dto.user_role.response.UserRoleResponse;
import com.example.mang_xa_hoi.entity.Role;
import com.example.mang_xa_hoi.entity.User;
import com.example.mang_xa_hoi.common.ApiResponse;
import com.example.mang_xa_hoi.entity.UserRole;
import com.example.mang_xa_hoi.exception.ValidationException;
import com.example.mang_xa_hoi.repository.RoleRepository;
import com.example.mang_xa_hoi.util.DynamicFilter;
import com.example.mang_xa_hoi.util.SpecificationBuilder;
import com.example.mang_xa_hoi.repository.UserRepository;
import com.example.mang_xa_hoi.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SpecificationBuilder specificationBuilder;
    private final DynamicFilter dynamicFilter;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> listUser(SearchParams params) {
        Map<String, Object> filters = dynamicFilter.toFilterMap(params);
        filters.put("deletedAt", null); // chỉ lấy record chưa xóa
        Specification<User> spec = specificationBuilder.build(filters);
        List<User> users = userRepository.findAll(spec);

        return users.stream().map(user -> {
            UserResponse response = new UserResponse();
            response.setId(user.getId());
            response.setCode(user.getCode());
            response.setName(user.getName());
            response.setUsername(user.getUsername());

            if (user.getUserRoles() != null) {
                List<UserRoleResponse> userRoles = user.getUserRoles().stream().map(ur -> {
                    UserRoleResponse urRes = new UserRoleResponse();
                    urRes.setId(ur.getRole().getId());
                    urRes.setRoleCode(ur.getRole().getCode());
                    urRes.setRoleName(ur.getRole().getName());
                    return urRes;
                }).toList();
                response.setUserRoles(userRoles);
            }

            return response;
        }).toList();
    }

    @Override
    public UserResponse findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy với id: " + id));

        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setCode(user.getCode());
        response.setName(user.getName());
        response.setUsername(user.getUsername());

        if (user.getUserRoles() != null) {
            List<UserRoleResponse> userRoles = user.getUserRoles().stream().map(ur -> {
                UserRoleResponse urRes = new UserRoleResponse();
                urRes.setId(ur.getRole().getId());
                urRes.setRoleCode(ur.getRole().getCode());
                urRes.setRoleName(ur.getRole().getName());
                return urRes;
            }).toList();
            response.setUserRoles(userRoles);
        }

        return response;
    }

    @Override
    public ApiResponse<UserResponse> createUser(UserRequest request) {
        validateUserRequest(request);

        User user = new User();
        user.setCode(request.getCode());
        user.setUsername(request.getCode() + "@gmail.com");
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode("123"));
        setUserRoles(user, request.getUserRoles());

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setCode(savedUser.getCode());
        response.setName(savedUser.getName());
        response.setUsername(savedUser.getUsername());
        response.setUserRoles(savedUser.getUserRoles().stream().map(ur -> {
            UserRoleResponse urRes = new UserRoleResponse();
            urRes.setId(ur.getRole().getId());
            urRes.setRoleCode(ur.getRole().getCode());
            urRes.setRoleName(ur.getRole().getName());
            return urRes;
        }).toList());

        return ApiResponse.success("Tạo thành công", response);
    }

    @Override
    public ApiResponse<UserResponse> updateUser(UserRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Người dùng không tồn tại"));

        validateUserRequest(request);

        user.setCode(request.getCode());
        user.setName(request.getName());
        setUserRoles(user, request.getUserRoles());

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setCode(savedUser.getCode());
        response.setName(savedUser.getName());
        response.setUsername(savedUser.getUsername());
        response.setUserRoles(savedUser.getUserRoles().stream().map(ur -> {
            UserRoleResponse urRes = new UserRoleResponse();
            urRes.setId(ur.getRole().getId());
            urRes.setRoleCode(ur.getRole().getCode());
            urRes.setRoleName(ur.getRole().getName());
            return urRes;
        }).toList());

        return ApiResponse.success("Cập nhật thành công", response);    }

    private void setUserRoles(User user, List<UserRoleRequest> roleRequests) {
        if (roleRequests == null) return;

        List<UserRole> roles = roleRequests.stream().map(urReq -> {
            UserRole ur = new UserRole();
            ur.setUser(user);

            Role role = roleRepository.findById(urReq.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role không tồn tại: " + urReq.getRoleId()));
            ur.setRole(role);

            return ur;
        }).toList();
        user.setUserRoles(roles);
    }

    private void validateUserRequest(UserRequest request) {
        Map<String, String> errors = new HashMap<>();

        if (request.getCode() == null || request.getCode().isBlank()) {
            errors.put("code", "Không được để trống");
        } else if (userRepository.existsByCode(request.getCode())) {
            errors.put("code", "Code đã tồn tại");
        }

        if (request.getName() == null || request.getName().isBlank()) {
            errors.put("name", "Không được để trống");
        } else if (userRepository.existsByName(request.getName())) {
            errors.put("name", "Tên đã tồn tại");
        }

        String username = request.getCode() + "@gmail.com";
        if (userRepository.existsByUsername(username)) {
            errors.put("username", "Username đã tồn tại");
        }

        if (request.getUserRoles() == null || request.getUserRoles().isEmpty()) {
            errors.put("userRoles", "Không được để trống");
        } else {
            for (int i = 0; i < request.getUserRoles().size(); i++) {
                if (request.getUserRoles().get(i).getRoleId() == null) {
                    errors.put("userRoles[" + i + "].roleId", "Không được để trống");
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}
