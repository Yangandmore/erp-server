package com.ls.erp.dao;

import com.ls.erp.entity.RoleInfo;
import com.ls.erp.entity.RoleUserInfo;
import com.ls.erp.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

    List<UserInfo> findAllUser();

    int addUser(@Param("u") UserInfo userInfo);

    int deleteUser(int id);

    int updateUser(@Param("u") UserInfo userInfo);

    boolean existsById(int id);

    int addRole(List<RoleUserInfo> roleInfoList);

    boolean existsUserRoleById(int id);

    int deleteRole(List<RoleUserInfo> roleInfoList);

    UserInfo findUserById(int id);

    List<RoleInfo> findUserRoleById(int id);
}
