package com.example.mang_xa_hoi.dto.favourite.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FavouriteResponse {

    private Long id;

    private LocalDateTime likeDate;

    private Long userId;
}
