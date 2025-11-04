package com.example.mang_xa_hoi.entity;

import com.example.mang_xa_hoi.entity.base.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
@Table(name = "permission")
public class Permission extends BaseEntity {

    private String apiPath;

    private String httpMethod;

    @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY)
    private List<RolePermission> rolePermissions;
}
