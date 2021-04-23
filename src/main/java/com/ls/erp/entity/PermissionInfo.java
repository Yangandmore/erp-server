package com.ls.erp.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Data
@Entity
@Table(name="PERMISSION")
@EntityListeners(AuditingEntityListener.class)
public class PermissionInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "permission_name")
    private String permissionName;

    @Column(name = "description")
    private String description;
}
