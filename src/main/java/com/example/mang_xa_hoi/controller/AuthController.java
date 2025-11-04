package com.example.mang_xa_hoi.controller;

import com.example.mang_xa_hoi.common.ApiResponse;
import com.example.mang_xa_hoi.dto.auth.request.ChangePasswordRequest;
import com.example.mang_xa_hoi.dto.auth.request.ForgotPasswordRequest;
import com.example.mang_xa_hoi.dto.auth.request.LoginRequest;
import com.example.mang_xa_hoi.dto.auth.request.RegisterRequest;
import com.example.mang_xa_hoi.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<?>> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return authService.forgotPassword(request);
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<?>> changePassword(@RequestBody ChangePasswordRequest request) {
        return authService.changePassword(request);
    }
}
