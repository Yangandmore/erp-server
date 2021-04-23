package com.ls.erp.dao;

import com.ls.erp.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoDao  extends JpaRepository<UserInfo, Integer> {

    List<UserInfo> findAll();
}
