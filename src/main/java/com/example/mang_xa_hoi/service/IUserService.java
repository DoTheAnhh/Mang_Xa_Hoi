package com.example.mang_xa_hoi.service;

import com.example.mang_xa_hoi.common.SearchParams;
import com.example.mang_xa_hoi.dto.user.request.UserRequest;
import com.example.mang_xa_hoi.dto.user.response.UserResponse;
import com.example.mang_xa_hoi.common.ApiResponse;

import java.util.List;

public interface IUserService {
    List<UserResponse> listUser(SearchParams params);
    UserResponse findUserById(Long id);
    ApiResponse<UserResponse> createUser(UserRequest userRequest);
    ApiResponse<UserResponse> updateUser(UserRequest request, Long userId);
}
