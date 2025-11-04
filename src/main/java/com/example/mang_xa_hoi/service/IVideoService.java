package com.example.mang_xa_hoi.service;

import com.example.mang_xa_hoi.common.SearchParams;
import com.example.mang_xa_hoi.dto.video.response.VideoResponse;

import java.util.List;

public interface IVideoService {
    List<VideoResponse> listVideo(SearchParams params);
}
