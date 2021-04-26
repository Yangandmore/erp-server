package com.ls.erp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name="ROLE")
@EntityListeners(AuditingEntityListener.class)
public class RoleInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "description")
    private String description;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name = "PERMISSION_ROLE", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<PermissionInfo> permissionInfoList;
}
