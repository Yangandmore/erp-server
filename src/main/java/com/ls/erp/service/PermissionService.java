package com.ls.erp.service;

import com.ls.erp.dao.PermissionDao;
import com.ls.erp.dao.UserInfoDao;
import com.ls.erp.entity.PermissionInfo;
import com.ls.erp.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    // 判断是否存在想通过PermissionName权限
    public boolean hasPermissionName(String permissionName) {
        return permissionDao.existsByPermissionName(permissionName).bool();
    }

    // 新增权限
    public void addPermission(PermissionInfo permissionInfo) {
        permissionDao.saveAndFlush(permissionInfo);
    }

    // 删除
    public void deletePermission(PermissionInfo permissionInfo) {
        permissionDao.delete(permissionInfo);
    }

    // 查找所有user
    public List<PermissionInfo> findAllPermission() {
        List<PermissionInfo> permissionInfoList = permissionDao.findAll();
        if (permissionInfoList == null) {
            return new ArrayList<>();
        }
        return permissionInfoList;
    }

    // 查询指定
    public boolean findOnePermission(PermissionInfo permissionInfo) {
        return permissionDao.existsById(permissionInfo.getId());
    }

}
