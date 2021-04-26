package com.ls.erp.service;

import com.ls.erp.dao.PermissionDao;
import com.ls.erp.entity.PermissionInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    // 判断是否存在想通过PermissionName权限
    public boolean existsId(int id) {
        return permissionDao.existsId(id);
    }

    // 判断是否存在想通过PermissionName权限
    public boolean existsPermissionName(String permissionName) {
        return permissionDao.existsPermissionName(permissionName);
    }

    // 新增权限
    public void addPermission(PermissionInfo permissionInfo) {
        permissionDao.addPermission(permissionInfo);
    }

    // 删除
    public void deletePermission(PermissionInfo permissionInfo) {
        permissionDao.delete(permissionInfo.getId());
    }

    // 改
    public void updatePermission(int id, PermissionInfo newPermissionInfo) {
        permissionDao.update(id, newPermissionInfo);
    }

    // 查找所有user
    public List<PermissionInfo> findAllPermission() {
        return permissionDao.findAll();
    }

}
