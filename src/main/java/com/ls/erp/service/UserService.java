package com.ls.erp.service;

import com.ls.erp.dao.RoleDao;
import com.ls.erp.dao.UserDao;
import com.ls.erp.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;


    // 查找所有user
    public List<UserInfo> findAllUser() {
        List<UserInfo> userInfoList = userDao.findAllUser();
        if (userInfoList == null) {
            return new ArrayList<>();
        }
        return userInfoList;
    }

    public void addUser(UserInfo userInfo) {
        userDao.addUser(userInfo);
    }

    public void deleteUser(UserInfo userInfo) {
        userDao.deleteUser(userInfo.getId());
    }

    public void updateUser(UserInfo userInfo) {
        userDao.updateUser(userInfo);
    }

    public boolean existsById(int id) {
        return userDao.existsById(id);
    }

    public void addRole(int uId, List<RoleInfo> roleInfoList) {
        Iterator<RoleInfo> iterator = roleInfoList.iterator();
        while (iterator.hasNext()) {
            boolean flag = roleDao.existsById(iterator.next().getId());
            if (!flag) {
                iterator.remove();
            }
        }

        List<RoleUserInfo> roleUserList = new ArrayList<>();
        for (RoleInfo rInfo:
                roleInfoList) {
            RoleUserInfo pr = new RoleUserInfo();
            pr.setRId(rInfo.getId());
            pr.setUId(uId);
            roleUserList.add(pr);
        }
        //添加至中间表
        userDao.addRole(roleUserList);
    }

    public void deleteRole(int uId, List<RoleInfo> roleInfoList) {
        List<RoleUserInfo> roleUserInfos = new ArrayList<>();
        Iterator<RoleInfo> iterator = roleInfoList.iterator();
        while (iterator.hasNext()) {
            RoleUserInfo r = new RoleUserInfo();
            r.setUId(uId);
            r.setRId(iterator.next().getId());
            roleUserInfos.add(r);
        }
        userDao.deleteRole(roleUserInfos);
    }

    public boolean existsUserRoleById(int id) {
        return userDao.existsUserRoleById(id);
    }

    public UserInfo findUserById(int id) {
        return userDao.findUserById(id);
    }

    public List<RoleInfo> findUserRoleById(int id) {
        return userDao.findUserRoleById(id);
    }
}
