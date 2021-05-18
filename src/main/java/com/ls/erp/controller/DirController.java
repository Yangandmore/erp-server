package com.ls.erp.controller;

import com.ls.erp.annotation.PassAuthToken;
import com.ls.erp.entity.DirInfo;
import com.ls.erp.entity.PermissionInfo;
import com.ls.erp.entity.ResultInfo;
import com.ls.erp.service.DirService;
import com.ls.erp.utils.LogUtil;
import com.mysql.cj.core.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "字典管理")
public class DirController {

    @Resource
    LogUtil logUtil;

    @Autowired
    private DirService dirService;

    // 增
    @PostMapping("/dir")
    @ApiOperation("新增字典")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
    })
    public ResultInfo addDir(DirInfo dirInfo) {
        logUtil.in("请求新增字典");
        if (dirInfo == null || StringUtils.isNullOrEmpty(dirInfo.getDirKey())) {
            logUtil.out("请求新增字典 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        if (dirService.existsDirKey(dirInfo.getDirKey())) {
            logUtil.out("存在相同字典名称");
            return ResultInfo.error("存在相同字典名称");
        }
        dirService.addDir(dirInfo);

        logUtil.out("请求新增字典成功");
        return ResultInfo.success("新增成功");
    }

    // 删
    @DeleteMapping("/dir")
    @ApiOperation("删除字典")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
    })
    public ResultInfo deleteDir(DirInfo res) {
        logUtil.in("请求删除字典");
        if (res == null || res.getId() == 0) {
            logUtil.out("请求字典 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        if (!dirService.existsDirId(res.getId())) {
            logUtil.out("未找到指定对象");
            return ResultInfo.error("未找到指定对象");
        }
        dirService.deleteDir(res.getId());

        logUtil.out("请求删除字典成功");
        return ResultInfo.success("删除成功");
    }

    // 改
    @PutMapping("/dir")
    @ApiOperation("修改字典")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
    })
    public ResultInfo updateDir(DirInfo dirInfo) {
        logUtil.in("请求修改修改字典");
        if (dirInfo == null || dirInfo.getId() == 0) {
            logUtil.out("请求修改字典 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        if (!dirService.existsDirId(dirInfo.getId())) {
            logUtil.out("未找到指定对象");
            return ResultInfo.error("未找到指定对象");
        }
        dirService.updateDir(dirInfo);

        logUtil.out("请求修改字典成功");
        return ResultInfo.success("修改成功");
    }

    // 查
    @GetMapping("/dir/all")
    @ApiOperation("查询所有字典")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
    })
    public ResultInfo getAll() {
        logUtil.in("请求查询所有字典");
        List<DirInfo> dirInfos = dirService.findAllDir();
        if (dirInfos == null) {
            dirInfos = new ArrayList<>();
        }
        logUtil.out("请求查询所有字典成功");
        return ResultInfo.success(dirInfos, "请求成功");
    }
}
