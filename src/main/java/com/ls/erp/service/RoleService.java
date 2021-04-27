package com.ls.erp.service;

import com.ls.erp.dao.PermissionDao;
import com.ls.erp.dao.RoleDao;
import com.ls.erp.entity.PermissionInfo;
import com.ls.erp.entity.PermissionRoleInfo;
import com.ls.erp.entity.RoleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PermissionDao permissionDao;

    public boolean existsRoleName(String name) {
        return roleDao.existsByRoleName(name);
    }

    public boolean existsRoleById(int id) {
        return roleDao.existsById(id);
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
        // 是否存在这些权限
        Iterator<PermissionInfo> iterator = permissionInfoList.iterator();
        while (iterator.hasNext()) {
            boolean flag = permissionDao.existsId(iterator.next().getId());
            if (!flag) {
                iterator.remove();
            }
        }

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

    public void deletePermission(RoleInfo roleInfo, List<PermissionInfo> permissionInfoList) {
        List<PermissionRoleInfo> permissionRoleInfos = new ArrayList<>();
        Iterator<PermissionInfo> iterator = permissionInfoList.iterator();
        while (iterator.hasNext()) {
            PermissionRoleInfo p = new PermissionRoleInfo();
            p.setRId(roleInfo.getId());
            p.setPId(iterator.next().getId());
            permissionRoleInfos.add(p);
        }
        roleDao.deletePermission(permissionRoleInfos);
    }

    public RoleInfo findRoleById(int roleId) {
        return roleDao.findById(roleId);
    }

    public boolean existsRolePermissionById(int id) {
        return roleDao.existsRolePermissionById(id);
    }
}
