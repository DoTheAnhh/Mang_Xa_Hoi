package com.example.mang_xa_hoi.dto.share.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShareResponse {

    private Long id;

    private String emails;

    private LocalDateTime shareDate;

    private Long userId;
}
