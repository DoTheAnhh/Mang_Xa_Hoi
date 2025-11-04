package com.example.mang_xa_hoi.controller;

import com.example.mang_xa_hoi.common.SearchParams;
import com.example.mang_xa_hoi.dto.user.request.UserRequest;
import com.example.mang_xa_hoi.dto.user.response.UserResponse;
import com.example.mang_xa_hoi.service.IUserService;
import com.example.mang_xa_hoi.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final IUserService userService;

    @PostMapping("list")
    public ResponseEntity<List<UserResponse>> listUser(@RequestBody SearchParams params) {
        List<UserResponse> users = userService.listUser(params);
        return ResponseEntity.ok(users);
    }

    @GetMapping("get/{userId}")
    public ResponseEntity<UserResponse> findUserById(@PathVariable Long userId) {
        UserResponse user = userService.findUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PostMapping("create")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody UserRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PutMapping("update/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@RequestBody UserRequest request,
                                                                @PathVariable Long userId) {
        return ResponseEntity.ok(userService.updateUser(request, userId));
    }
}
