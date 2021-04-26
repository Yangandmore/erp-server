package com.ls.erp.controller;

import com.ls.erp.entity.PermissionInfo;
import com.ls.erp.entity.ResultInfo;
import com.ls.erp.service.PermissionService;
import com.ls.erp.utils.LogUtil;
import com.mysql.cj.core.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "权限管理")
public class PermissionController {

    @Resource
    LogUtil logUtil;

    @Autowired
    PermissionService permissionService;

    // 增
    @PostMapping("/permission")
    @ApiOperation("新增权限信息")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
    })
    public ResultInfo add(PermissionInfo res) {
        logUtil.in("请求新增权限信息");
        if (res == null || StringUtils.isNullOrEmpty(res.getPermissionName())) {
            logUtil.out("请求新增权限信息 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        if (permissionService.existsPermissionName(res.getPermissionName())) {
            logUtil.out("存在相同权限名称");
            return ResultInfo.error("存在相同权限名称");
        }
        permissionService.addPermission(res);

        logUtil.out("请求新增权限信息成功");
        return ResultInfo.success("新增成功");
    }

    // 删
    @DeleteMapping("/permission")
    @ApiOperation("删除权限信息")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
    })
    public ResultInfo delete(PermissionInfo res) {
        logUtil.in("请求删除权限信息");
        if (res == null) {
            logUtil.out("请求删除权限信息 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        if (!permissionService.existsPermission(res)) {
            logUtil.out("未找到指定对象");
            return ResultInfo.error("未找到指定对象");
        }
        permissionService.deletePermission(res);

        logUtil.out("请求删除权限信息成功");
        return ResultInfo.success("删除成功");
    }

    // 改
    @PutMapping("/permission")
    @ApiOperation("修改权限信息")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
    })
    public ResultInfo update(PermissionInfo res) {
        logUtil.in("请求修改权限信息");
        if (res == null || (StringUtils.isNullOrEmpty(res.getPermissionName()) && StringUtils.isNullOrEmpty(res.getDescription()))) {
            logUtil.out("请求修改权限信息 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        if (!permissionService.existsPermission(res)) {
            logUtil.out("未找到指定对象");
            return ResultInfo.error("未找到指定对象");
        }
        permissionService.addPermission(res);

        logUtil.out("请求修改权限信息成功");
        return ResultInfo.success("修改成功");
    }

    // 查
    @GetMapping("/permission")
    @ApiOperation("查询所有权限信息")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
    })
    public ResultInfo findAll() {
        logUtil.in("请求查询所有权限信息");
        List<PermissionInfo> permissionInfoList = permissionService.findAllPermission();
        logUtil.out("请求查询所有权限信息成功");
        return ResultInfo.success(permissionInfoList, "请求成功");
    }

}
