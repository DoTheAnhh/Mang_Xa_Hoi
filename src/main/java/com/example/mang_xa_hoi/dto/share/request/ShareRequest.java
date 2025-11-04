package com.example.mang_xa_hoi.dto.share.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ShareRequest {

    private String emails;

    private LocalDateTime shareDate;

    private Long userId;
}
