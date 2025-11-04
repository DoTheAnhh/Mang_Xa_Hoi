package com.example.mang_xa_hoi.controller.render;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("jsp")
@RequiredArgsConstructor
public class PageController {

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }

    @GetMapping("forgot-password")
    public String forgotPassword() {
        return "forgot-password";
    }

    @GetMapping("change-password")
    public String changePassword() {
        return "change-password";
    }

    @GetMapping("user")
    public String user() {
        return "user";
    }

    @GetMapping("role")
    public String role() {
        return "role";
    }

    @GetMapping("video")
    public String video() {
        return "video";
    }

    @GetMapping("share")
    public String share() {
        return "share";
    }

    @GetMapping("favourite")
    public String favourite() {
        return "favourite";
    }

    @GetMapping("permission")
    public String permission() {
        return "permission";
    }
}
