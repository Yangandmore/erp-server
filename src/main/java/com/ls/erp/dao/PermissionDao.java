package com.ls.erp.dao;

import com.ls.erp.entity.PermissionInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermissionDao {

    boolean existsId(int id);
    boolean existsPermissionName(String permissionName);

    int addPermission(PermissionInfo permissionInfo);

    int delete(int id);

    int update(@Param("id")int id, @Param("p") PermissionInfo permissionInfo);

    List<PermissionInfo> findAll();
}
