package com.ls.erp.service;

import com.ls.erp.dao.RoleDao;
import com.ls.erp.entity.PermissionInfo;
import com.ls.erp.entity.PermissionRoleInfo;
import com.ls.erp.entity.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public boolean existsRoleName(String name) {
        return roleDao.existsByRoleName(name);
    }

    public boolean existsRoleById(RoleInfo roleInfo) {
        return roleDao.existsById(roleInfo.getId());
    }

    public void addRole(RoleInfo roleInfo) {
        roleDao.addRole(roleInfo);
    }

    public void deleteRole(RoleInfo roleInfo) {
        roleDao.delete(roleInfo.getId());
    }

    public void update(int id, RoleInfo roleInfo) {
        roleDao.update(id, roleInfo);
    }

    public List<RoleInfo> findAll() {
        return roleDao.findAll();
    }

    public void addPermission(RoleInfo roleInfo, List<PermissionInfo> permissionInfoList) {
        List<PermissionRoleInfo> permissionRoleList = new ArrayList<>();
        for (PermissionInfo pInfo:
             permissionInfoList) {
            PermissionRoleInfo pr = new PermissionRoleInfo();
            pr.setRId(roleInfo.getId());
            pr.setPId(pInfo.getId());
            permissionRoleList.add(pr);
        }
        //添加至中间表
        roleDao.addPermission(permissionRoleList);
    }

    public RoleInfo findRoleById(int roleId) {
        return roleDao.findById(roleId);
    }
}
