package com.example.mang_xa_hoi.controller;

import com.example.mang_xa_hoi.dto.favourite.request.FavouriteRequest;
import com.example.mang_xa_hoi.dto.favourite.response.FavouriteResponse;
import com.example.mang_xa_hoi.entity.Favourite;
import com.example.mang_xa_hoi.service.impl.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/favourite")
public class FavouriteController {
    @Autowired
     private FavouriteService favouriteService;
    @PostMapping("/add")
    public ResponseEntity<?> addFavourite(@RequestBody FavouriteRequest request) {
        FavouriteResponse response = favouriteService.create(request);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<FavouriteResponse> updateFavourite(
            @PathVariable Long id,
            @RequestBody FavouriteRequest request) {
        FavouriteResponse response = favouriteService.update(id, request);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavourite(@PathVariable Long id) {
        favouriteService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/{id}")
    public ResponseEntity<FavouriteResponse> getFavouriteById(@PathVariable Long id) {
        FavouriteResponse response = favouriteService.getById(id)
                .orElseThrow(() -> new RuntimeException("Favourite not found"));
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<FavouriteResponse>> getAllFavourites() {
        List<FavouriteResponse> responses = favouriteService.getAll();
        return ResponseEntity.ok(responses);
    }
    @GetMapping("/search")
    public ResponseEntity<List<FavouriteResponse>> findByLikeDateBetween(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        FavouriteRequest request = new FavouriteRequest();
        request.setFromDate(startDate);
        request.setToDate(endDate);

        List<FavouriteResponse> responses = favouriteService.searchByDateRange(request);
        return ResponseEntity.ok(responses);
    }

}
