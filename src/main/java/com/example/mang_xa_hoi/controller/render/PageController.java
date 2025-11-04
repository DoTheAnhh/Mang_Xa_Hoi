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

    @GetMapping("user")
    public String user() {
        return "user";
    }
}
