package com.ls.erp.dao;

import com.ls.erp.entity.DirInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DirDao {
    List<DirInfo> findAll();

    boolean existsDirKey(String dirKey);

    void addDir(DirInfo dirInfo);

    boolean existsDirId(int id);

    void deleteDir(int id);

    void updateDir(@Param("d")DirInfo dirInfo);

}
