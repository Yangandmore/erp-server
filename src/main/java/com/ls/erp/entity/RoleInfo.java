package com.ls.erp.entity;

import lombok.Data;

import java.util.List;

@Data
public class RoleInfo {
    private int id;

    private String roleName;

    private String description;

    private List<PermissionInfo> permissionInfos;

}
