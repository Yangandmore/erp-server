package com.ls.erp.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Table(name="PERMISSION_ROLE")
@EntityListeners(AuditingEntityListener.class)
public class PermissionRole {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "permission_id")
    private int permissionId;

    @Column(name = "role_id")
    private int roleId;

    public PermissionRole(int permissionId, int roleId) {
        this.setPermissionId(permissionId);
        this.setRoleId(roleId);
    }


}
