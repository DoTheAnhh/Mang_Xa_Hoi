package com.example.mang_xa_hoi.service.impl;

import com.example.mang_xa_hoi.dto.favourite.request.FavouriteRequest;
import com.example.mang_xa_hoi.dto.favourite.response.FavouriteResponse;
import com.example.mang_xa_hoi.entity.Favourite;
import com.example.mang_xa_hoi.entity.User;
import com.example.mang_xa_hoi.repository.FavouriteRepository;
import com.example.mang_xa_hoi.repository.UserRepository;
import com.example.mang_xa_hoi.service.IFavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavouriteService implements IFavouriteService {

    private FavouriteRepository favouriteRepository;
    private UserRepository userRepository;
    @Override
    public FavouriteResponse create(FavouriteRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Favourite favourite = new Favourite();
        favourite.setLikeDate(request.getFromDate());
        favourite.setUser(user);

        Favourite saved = favouriteRepository.save(favourite);

        return mapToResponse(saved);
    }

    @Override
    public FavouriteResponse update(Long id, FavouriteRequest request) {
        Favourite favourite = favouriteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favourite not found"));

        favourite.setLikeDate(request.getToDate());

        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            favourite.setUser(user);
        }

        Favourite updated = favouriteRepository.save(favourite);
        return mapToResponse(updated);
    }

    @Override
    public void delete(Long id) {
        favouriteRepository.deleteById(id);
    }

    @Override
    public List<FavouriteResponse> getAll() {
        return List.of();
    }

    @Override
    public Optional<FavouriteResponse> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<FavouriteResponse> searchByDateRange(FavouriteRequest request) {
        return List.of();
    }

    @Override
    public List<FavouriteResponse> searchByUserAndDateRange(Long userId, FavouriteRequest request) {
        return List.of();
    }
    private FavouriteResponse mapToResponse(Favourite favourite) {
        FavouriteResponse response = new FavouriteResponse();
        response.setId(favourite.getId());
        response.setLikeDate(favourite.getLikeDate());
        response.setUserId(favourite.getUser().getId());
        return response;
    }
}
