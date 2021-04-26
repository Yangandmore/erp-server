package com.ls.erp.dao;

import com.ls.erp.entity.PermissionRoleInfo;
import com.ls.erp.entity.RoleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleDao {

    boolean existsById(int id);
    boolean existsByRoleName(String roleName);
    RoleInfo findById(int id);

    int addRole(RoleInfo roleInfo);

    int delete(int id);

    int update(@Param("id") int id, @Param("r") RoleInfo roleInfo);

    List<RoleInfo> findAll();

    int addPermission(List<PermissionRoleInfo> permissionInfoList);

}
