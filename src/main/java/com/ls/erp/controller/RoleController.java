package com.ls.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.ls.erp.entity.PermissionInfo;
import com.ls.erp.entity.PermissionRoleInfo;
import com.ls.erp.entity.ResultInfo;
import com.ls.erp.entity.RoleInfo;
import com.ls.erp.service.PermissionService;
import com.ls.erp.service.RoleService;
import com.ls.erp.utils.LogUtil;
import com.mysql.cj.core.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Api(tags = "角色管理")
@RestController
public class RoleController {

    @Resource
    LogUtil logUtil;

    @Autowired
    RoleService roleService;

    @PostMapping("/role")
    @ApiOperation("新增角色信息")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
    })
    public ResultInfo add(RoleInfo res) {
        logUtil.in("请求新增角色信息");
        if (res == null || StringUtils.isNullOrEmpty(res.getRoleName())) {
            logUtil.out("请求新增角色信息 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        if (roleService.existsRoleName(res.getRoleName())) {
            logUtil.out("存在相同角色名称");
            return ResultInfo.error("存在相同角色名称");
        }
        roleService.addRole(res);

        logUtil.out("请求新增角色信息成功");
        return ResultInfo.success("新增成功");
    }

    @DeleteMapping("/role")
    @ApiOperation("删除角色信息")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
    })
    public ResultInfo delete(RoleInfo res) {
        logUtil.in("请求删除角色信息");
        if (res == null) {
            logUtil.out("请求删除权限信息 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        if (!roleService.existsRoleById(res.getId())) {
            logUtil.out("未找到指定对象");
            return ResultInfo.error("未找到指定对象");
        }
        roleService.deleteRole(res);

        logUtil.out("请求删除角色信息成功");
        return ResultInfo.success("删除成功");
    }

    @PutMapping("/role")
    @ApiOperation("修改角色信息")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
    })
    public ResultInfo update(RoleInfo res) {
        logUtil.in("请求修改角色信息");
        if (res == null || (StringUtils.isNullOrEmpty(res.getRoleName()) && StringUtils.isNullOrEmpty(res.getDescription()))) {
            logUtil.out("请求修改权限信息 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        if (!roleService.existsRoleById(res.getId())) {
            logUtil.out("未找到指定对象");
            return ResultInfo.error("未找到指定对象");
        }
        roleService.update(res.getId(), res);

        logUtil.out("请求修改角色信息成功");
        return ResultInfo.success("修改成功");

    }

    @GetMapping("/role")
    @ApiOperation("查询所有角色信息")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
    })
    public ResultInfo findAll() {
        logUtil.in("请求查询所有角色信息");
        List<RoleInfo> roleInfoList = roleService.findAll();
        logUtil.out("请求查询所有角色信息成功");
        return ResultInfo.success(roleInfoList, "请求成功");
    }


    // 添加权限
    @PostMapping("/role/permission")
    @ApiOperation("给角色添加权限")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
    })
    public ResultInfo addPermission(@RequestBody Map<String, Object> req) {
        logUtil.in("请求给角色添加权限");
        if (req.size() == 0 || !req.containsKey("roleId") || !req.containsKey("permissions")) {
            logUtil.out("请求给角色添加权限 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        RoleInfo roleInfo = roleService.findRoleById((Integer) req.get("roleId"));
        if (roleInfo == null) {
            logUtil.out("请求给角色添加权限 角色未找到");
            return ResultInfo.error("角色未找到");
        }

        List<PermissionInfo> permissionInfoList = JSONObject.parseArray(req.get("permissions").toString(), PermissionInfo.class);

        roleService.addPermission(roleInfo, permissionInfoList);

        logUtil.out("请求给角色添加权限成功");
        return ResultInfo.success("添加权限成功");
    }

    // 删除权限
    @DeleteMapping("/role/permission")
    @ApiOperation("给角色删除权限")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
    })
    public ResultInfo deletePermission(@RequestBody Map<String, Object> req) {
        logUtil.in("请求给角色删除权限");
        if (req.size() == 0 || !req.containsKey("roleId") || !req.containsKey("permissions")) {
            logUtil.out("请求给角色添加权限 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        RoleInfo roleInfo = roleService.findRoleById((Integer) req.get("roleId"));
        if (roleInfo == null) {
            logUtil.out("请求给角色添加权限 角色未找到");
            return ResultInfo.error("角色未找到");
        }

        List<PermissionInfo> permissionInfoList = JSONObject.parseArray(req.get("permissions").toString(), PermissionInfo.class);

        roleService.deletePermission(roleInfo, permissionInfoList);

        logUtil.out("请求给角色删除权限成功");
        return ResultInfo.success("删除权限成功");
    }

}
