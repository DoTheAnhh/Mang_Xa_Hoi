package com.example.mang_xa_hoi.dto.video.request;

import com.example.mang_xa_hoi.dto.favourite.request.FavouriteRequest;
import com.example.mang_xa_hoi.dto.share.request.ShareRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class VideoRequest {

    private Long id;

    private String title;

    private String poster;

    private String videoUrl;

    private int views;

    private String description;

    private boolean active;

    private List<FavouriteRequest> favourites;

    private List<ShareRequest> shares;
}
