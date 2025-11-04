package com.example.mang_xa_hoi.controller;

import com.example.mang_xa_hoi.common.SearchParams;
import com.example.mang_xa_hoi.dto.video.response.VideoResponse;
import com.example.mang_xa_hoi.service.IVideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("video")
public class VideoController {

    private final IVideoService videoService;

    @PostMapping("list")
    public ResponseEntity<List<VideoResponse>> listUser(@RequestBody SearchParams params) {
        List<VideoResponse> users = videoService.listVideo(params);
        return ResponseEntity.ok(users);
    }
}
