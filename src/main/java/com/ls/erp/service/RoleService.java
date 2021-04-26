package com.ls.erp.service;

import com.ls.erp.dao.PermissionRoleDao;
import com.ls.erp.dao.RoleDao;
import com.ls.erp.entity.PermissionInfo;
import com.ls.erp.entity.PermissionRole;
import com.ls.erp.entity.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionRoleDao permissionRoleDao;

    public boolean existsRoleName(String name) {
        return roleDao.existsRoleInfoByRoleName(name).bool();
    }

    public boolean existsRole(RoleInfo roleInfo) {
        return roleDao.existsById(roleInfo.getId());
    }

    public void addRole(RoleInfo roleInfo) {
        roleDao.saveAndFlush(roleInfo);
    }

    public void deleteRole(RoleInfo roleInfo) {
        roleDao.delete(roleInfo);
    }

    public List<RoleInfo> findAll() {
        return roleDao.findAll();
    }

    public void addPermission(RoleInfo roleInfo, List<PermissionInfo> permissionInfoList) {
        List<PermissionRole> permissionRoleList = new ArrayList<>();
        for (PermissionInfo pInfo:
             permissionInfoList) {
            permissionRoleList.add(new PermissionRole(pInfo.getId(), roleInfo.getId()));
        }
        //添加至中间表
        permissionRoleDao.saveAll(permissionRoleList);
        permissionRoleDao.flush();
    }

    public RoleInfo findRoleById(int roleId) {
        return roleDao.findById(roleId).orElse(null);
    }
}
