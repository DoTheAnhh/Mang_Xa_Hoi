package com.example.mang_xa_hoi.service;

import com.example.mang_xa_hoi.dto.favourite.request.FavouriteRequest;
import com.example.mang_xa_hoi.dto.favourite.response.FavouriteResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IFavouriteService {
    FavouriteResponse create(FavouriteRequest request);

    FavouriteResponse update(Long id, FavouriteRequest request);

    void delete(Long id);

    List<FavouriteResponse> getAll();

    Optional<FavouriteResponse> getById(Long id);

    // 🔎 Tìm kiếm theo khoảng ngày
    List<FavouriteResponse> searchByDateRange(FavouriteRequest request);

    // 🔎 Tìm kiếm theo userId + khoảng ngày
    List<FavouriteResponse> searchByUserAndDateRange(Long userId, FavouriteRequest request);
}
