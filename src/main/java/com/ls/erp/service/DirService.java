package com.ls.erp.service;

import com.ls.erp.dao.DirDao;
import com.ls.erp.entity.DirInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirService {

    @Autowired
    private DirDao dirDao;

    // 增
    public void addDir(DirInfo dirInfo) {
        dirDao.addDir(dirInfo);
    }

    // 删
    public void deleteDir(int id) {
        dirDao.deleteDir(id);
    }

    // 改
    public void updateDir(DirInfo dirInfo) {
        dirDao.updateDir(dirInfo);
    }

    // 查
    public List<DirInfo> findAllDir() {
        return dirDao.findAll();
    }

    public boolean existsDirKey(String dirKey) {
        return dirDao.existsDirKey(dirKey);
    }

    public boolean existsDirId(int id) {
        return dirDao.existsDirId(id);
    }

}
