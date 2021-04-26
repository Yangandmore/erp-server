package com.ls.erp.dao;

import com.ls.erp.entity.PermissionInfo;
import com.ls.erp.entity.PermissionRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRoleDao extends JpaRepository<PermissionRole, Integer> {

}
