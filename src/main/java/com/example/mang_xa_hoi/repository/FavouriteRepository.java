package com.example.mang_xa_hoi.repository;

import com.example.mang_xa_hoi.dto.favourite.request.FavouriteRequest;
import com.example.mang_xa_hoi.entity.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite,Long> {
    @Query("SELECT f FROM Favourite f WHERE f.id = :id OR f.video.id = :videoId")
    Optional<FavouriteRequest> findByIdOrVideoId(@Param("id") Long id, @Param("videoId") Long videoId);

    @Query("SELECT f FROM Favourite f " +
            "WHERE (:fromDate IS NULL OR f.likeDate >= :fromDate) " +
            "AND (:toDate IS NULL OR f.likeDate <= :toDate)")
    List<Favourite> findByLikeDateBetween(@Param("fromDate") LocalDateTime fromDate,
                                          @Param("toDate") LocalDateTime toDate);

}
