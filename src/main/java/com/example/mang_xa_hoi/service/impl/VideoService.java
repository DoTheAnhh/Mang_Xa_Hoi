package com.example.mang_xa_hoi.service.impl;

import com.example.mang_xa_hoi.common.ApiResponse;
import com.example.mang_xa_hoi.common.SearchParams;
import com.example.mang_xa_hoi.dto.favourite.response.FavouriteResponse;
import com.example.mang_xa_hoi.dto.share.response.ShareResponse;
import com.example.mang_xa_hoi.dto.video.request.VideoRequest;
import com.example.mang_xa_hoi.dto.video.response.VideoResponse;
import com.example.mang_xa_hoi.entity.Video;
import com.example.mang_xa_hoi.repository.VideoRepository;
import com.example.mang_xa_hoi.service.IVideoService;
import com.example.mang_xa_hoi.util.DynamicFilter;
import com.example.mang_xa_hoi.util.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService implements IVideoService {

    private final VideoRepository videoRepository;
    private final SpecificationBuilder specificationBuilder;
    private final DynamicFilter dynamicFilter;

    @Override
    public List<VideoResponse> listVideo(SearchParams params) {
        Map<String, Object> filters = dynamicFilter.toFilterMap(params);
        Specification<Video> spec = specificationBuilder.build(filters);
        List<Video> videos = videoRepository.findAll(spec);

        return videos.stream().map(video -> {
            VideoResponse response = new VideoResponse();
            response.setId(video.getId());
            response.setTitle(video.getTitle());
            response.setPoster(video.getPoster());
            response.setVideoUrl(video.getVideoUrl());
            response.setViews(video.getViews());
            response.setDescription(video.getDescription());
            response.setActive(video.isActive() ? "Hoạt động" : "Không hoạt động");
            response.setFavourites(
                    video.getFavourites().stream()
                            .map(fav -> new FavouriteResponse(
                                    fav.getId(),
                                    fav.getLikeDate(),
                                    fav.getUser().getId()
                            ))
                            .collect(Collectors.toList())
            );
            response.setShares(
                    video.getShares().stream()
                            .map(share -> new ShareResponse(
                                    share.getId(),
                                    share.getEmails(),
                                    share.getShareDate(),
                                    share.getUser().getId()
                            ))
                            .collect(Collectors.toList())
            );

            return response;
        }).toList();
    }

    public ApiResponse<VideoResponse> createVideo(VideoRequest request){
        return null;
    }
}
