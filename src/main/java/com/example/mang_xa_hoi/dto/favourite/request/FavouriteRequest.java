package com.example.mang_xa_hoi.dto.favourite.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class FavouriteRequest {

    private LocalDateTime likeDate;

    private Long userId;
}
