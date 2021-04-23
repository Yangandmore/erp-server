package com.ls.erp.dao;

import com.ls.erp.entity.PermissionInfo;
import com.sun.org.apache.xpath.internal.objects.XBoolean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionDao  extends JpaRepository<PermissionInfo, Integer> {

    XBoolean existsByPermissionName(String permissionName);
    List<PermissionInfo> findAll();
}
