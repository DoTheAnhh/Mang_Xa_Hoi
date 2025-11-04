package com.example.mang_xa_hoi.dto.video.response;

import com.example.mang_xa_hoi.dto.favourite.response.FavouriteResponse;
import com.example.mang_xa_hoi.dto.share.response.ShareResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VideoResponse {

    private Long id;

    private String title;

    private String poster;

    private String videoUrl;

    private int views;

    private String description;

    private String active;

    private List<ShareResponse> shares;

    private List<FavouriteResponse> favourites;
}
