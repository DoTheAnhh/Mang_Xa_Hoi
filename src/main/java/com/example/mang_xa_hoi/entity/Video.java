package com.example.mang_xa_hoi.entity;

import com.example.mang_xa_hoi.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "video")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String poster;

    private String videoUrl;

    private int views;

    private String description;

    private boolean active;

    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Favourite> favourites;

    @OneToMany(mappedBy = "video", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Share> shares;

    //Nguời đăng
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User uploader;
}
