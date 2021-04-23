package com.ls.erp.service;

import com.ls.erp.dao.UserInfoDao;
import com.ls.erp.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServer {

    @Autowired
    private UserInfoDao userInfoDao;


    // 查找所有user
    public List<UserInfo> findAllUser() {
        List<UserInfo> userInfoList = userInfoDao.findAll();
        if (userInfoList == null) {
            return new ArrayList<>();
        }
        return userInfoList;
    }
}
