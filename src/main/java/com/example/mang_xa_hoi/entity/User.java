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
@Table(name = "user")
public class User extends BaseEntity {

    private String username;

    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserRole> userRoles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Favourite> favourites;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Share> shares;

    @OneToMany(mappedBy = "follower", fetch = FetchType.LAZY)
    private List<Follow> following; // người mình theo dõi

    @OneToMany(mappedBy = "followed", fetch = FetchType.LAZY)
    private List<Follow> followers; // người theo dõi mình
}
