package com.ls.erp.dao;

import com.ls.erp.entity.RoleInfo;
import com.sun.org.apache.xpath.internal.objects.XBoolean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends JpaRepository<RoleInfo, Integer> {

    XBoolean existsRoleInfoByRoleName(String name);
}
