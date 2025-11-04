package com.example.mang_xa_hoi.service.impl;

import com.example.mang_xa_hoi.common.ApiResponse;
import com.example.mang_xa_hoi.dto.auth.request.ChangePasswordRequest;
import com.example.mang_xa_hoi.dto.auth.request.ForgotPasswordRequest;
import com.example.mang_xa_hoi.dto.auth.request.LoginRequest;
import com.example.mang_xa_hoi.dto.auth.request.RegisterRequest;
import com.example.mang_xa_hoi.entity.User;
import com.example.mang_xa_hoi.exception.ValidationException;
import com.example.mang_xa_hoi.repository.UserRepository;
import com.example.mang_xa_hoi.security.JwtUtil;
import com.example.mang_xa_hoi.service.IAuthService;
import com.example.mang_xa_hoi.util.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    // Repository thao tác với bảng User
    private final UserRepository userRepository;

    private final EmailService emailService;

    // Bean PasswordEncoder (BCrypt) để mã hóa và kiểm tra mật khẩu
    private final PasswordEncoder passwordEncoder;

    // Bean JwtUtil để tạo và kiểm tra JWT token
    private final JwtUtil jwtUtil;

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> register(RegisterRequest request) {

        validateLoginAndRegister(request.getUsername(), request.getPassword());

        // Kiểm tra username đã tồn tại chưa
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST) // trả 400 nếu đã tồn tại
                    .body(ApiResponse.error("Tài khoản đã tồn tại"));
        }

        // Tạo user mới
        User user = new User();
        user.setCode(request.getUsername());
        user.setUsername(request.getUsername());
        user.setName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // mã hóa mật khẩu
        userRepository.save(user);

        // Tạo JWT token
        String token = jwtUtil.generateToken(user);
        Map<String, Object> data = new HashMap<>();
        data.put("token", token);
        data.put("username", user.getUsername());

        // Tự động login sau khi đăng ký
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getUsername(), null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Trả về 200 + token
        return ResponseEntity.ok(ApiResponse.success("Đăng ký thành công", data));
    }

    @Override
    public ResponseEntity<ApiResponse<?>> login(LoginRequest request) {

        validateLoginAndRegister(request.getUsername(), request.getPassword());

        // Lấy user từ DB theo username
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);

        // Nếu user không tồn tại hoặc mật khẩu sai, trả 401
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED) // 401: sai username/mật khẩu
                    .body(ApiResponse.error("Sai tài khoản hoặc mật khẩu"));
        }

        // Tạo JWT token hợp lệ
        String token = jwtUtil.generateToken(user);

        // Set Authentication vào SecurityContext để các filter sau nhận biết user đã login
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getUsername(), null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Map<String, Object> data = new HashMap<>();

        //Lấy ra list role
        List<String> roles = user.getUserRoles()
                .stream()
                .map(userRole -> userRole.getRole().getName())
                .toList();

        data.put("token", token);
        data.put("roles", roles);
        data.put("username", user.getUsername());

        // Trả về 200 + token
        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công", data));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Tài khoản không tồn tại"));
        }

        String tempPassword = generateRandomPassword(8);
        user.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);

        try {
            emailService.sendEmail(user.getUsername(), "Mật khẩu mới",
                    "Mật khẩu mới của bạn là: " + tempPassword + "\nHãy đổi mật khẩu sau khi đăng nhập!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Gửi email thất bại"));
        }

        return ResponseEntity.ok(ApiResponse.success("Mật khẩu mới đã được gửi đến email của bạn"));
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse<?>> changePassword(ChangePasswordRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error("Tài khoản không tồn tại"));
        }

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Mật khẩu cũ không đúng"));
        }

        String validationError = validateChangePassword(request.getNewPassword(), user.getPassword());
        if (validationError != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(validationError));
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        return ResponseEntity.ok(ApiResponse.success("Đổi mật khẩu thành công"));
    }

    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int idx = (int) (Math.random() * chars.length());
            sb.append(chars.charAt(idx));
        }
        return sb.toString();
    }

    private String validateChangePassword(String newPassword, String oldEncodedPassword) {
        if (newPassword == null || newPassword.length() < 3) {
            return "Mật khẩu mới phải có ít nhất 3 ký tự";
        }
        if (passwordEncoder.matches(newPassword, oldEncodedPassword)) {
            return "Mật khẩu mới không được trùng mật khẩu cũ";
        }
        return null;
    }

    private void validateLoginAndRegister(String username, String password) {
        Map<String, String> errors = new HashMap<>();

        if (username == null || username.isBlank()) {
            errors.put("username", "Không được để trống tài khoản");
        }

        if (password == null || password.isBlank()) {
            errors.put("password", "Không được để trống mật khẩu");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}

