package com.example.mang_xa_hoi.service;

import com.example.mang_xa_hoi.common.ApiResponse;
import com.example.mang_xa_hoi.dto.auth.request.ChangePasswordRequest;
import com.example.mang_xa_hoi.dto.auth.request.ForgotPasswordRequest;
import com.example.mang_xa_hoi.dto.auth.request.LoginRequest;
import com.example.mang_xa_hoi.dto.auth.request.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    ResponseEntity<ApiResponse<?>> login(LoginRequest request);
    ResponseEntity<ApiResponse<?>> register(RegisterRequest request);
    ResponseEntity<ApiResponse<?>> forgotPassword(ForgotPasswordRequest request);
    ResponseEntity<ApiResponse<?>> changePassword(ChangePasswordRequest request);
}
