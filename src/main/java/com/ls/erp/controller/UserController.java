package com.ls.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.ls.erp.entity.ResultInfo;
import com.ls.erp.entity.RoleInfo;
import com.ls.erp.entity.RoleUserInfo;
import com.ls.erp.entity.UserInfo;
import com.ls.erp.service.RoleService;
import com.ls.erp.service.UserService;
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

@RestController
@Api(tags = "用户管理接口")
public class UserController {

    @Resource
    LogUtil logUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    // 用户登陆

    // 新增用户
    @PostMapping("/user")
    @ApiOperation("添加用户")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
    })
    public ResultInfo addUser(UserInfo userInfo) {
        logUtil.in("请求添加用户");

        if (userInfo == null || StringUtils.isNullOrEmpty(userInfo.getLoginName()) || StringUtils.isNullOrEmpty(userInfo.getPassword()) || StringUtils.isNullOrEmpty(userInfo.getName())) {
            logUtil.out("请求给角色添加权限 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        userService.addUser(userInfo);

        logUtil.out("请求添加用户成功");
        return ResultInfo.success("添加成功");
    }

    // 删除用户
    @DeleteMapping("/user")
    @ApiOperation("删除用户")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
    })
    public ResultInfo deleteUser(UserInfo userInfo) {
        logUtil.in("请求删除用户");

        if (userInfo == null) {
            logUtil.out("请求删除角色 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        userService.deleteUser(userInfo);

        logUtil.out("请求删除用户成功");
        return ResultInfo.success("删除成功");
    }

    // 编辑用户
    @PutMapping("/user")
    @ApiOperation("编辑用户")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
    })
    public ResultInfo updateUser(UserInfo userInfo) {
        logUtil.in("请求编辑用户");

        if (userInfo == null) {
            logUtil.out("请求编辑用户 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        if (!userService.existsById(userInfo.getId())) {
            logUtil.out("用户未找到");
            return ResultInfo.error("用户未找到");
        }
        userService.updateUser(userInfo);

        logUtil.out("请求编辑用户成功");
        return ResultInfo.success("编辑成功");
    }

    // 查询用户
    @GetMapping("/user/all")
    @ApiOperation("查询所有用户")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
    })
    public ResultInfo findAllUser() {
        logUtil.in("请求查询所有用户");
        List<UserInfo> userInfoList = userService.findAllUser();
        logUtil.out("请求查询所有用户成功");
        return ResultInfo.success(userInfoList, "查询成功");
    }

    // 查询相关用户的角色
    @GetMapping("/user/role/{id}")
    @ApiOperation("查看用户角色")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
    })
    public ResultInfo listRole(@PathVariable("id")int id) {
        logUtil.in("请求给用户添加角色");

        if (!userService.existsById(id)) {
            // 用户未找到
            logUtil.out("请求查询用户角色 用户未找到");
            return ResultInfo.error("用户未找到");
        }
        List<RoleInfo> roleInfoList = userService.findUserRoleById(id);

        logUtil.out("请求给用户添加角色成功");
        return ResultInfo.success(roleInfoList, "查询成功");
    }

    // 添加角色
    @PostMapping("/user/role")
    @ApiOperation("给用户添加角色")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
    })
    public ResultInfo addRole(@RequestBody Map<String, Object> req) {
        logUtil.in("请求给用户添加角色");
        if (req.size() == 0 || !req.containsKey("userId") || !req.containsKey("roles")) {
            logUtil.out("请求给用户添加角色 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        int userId = (int) req.get("userId");
        if (!userService.existsById(userId)) {
            logUtil.out("请求给用户添加角色 用户未找到");
            return ResultInfo.error("用户未找到");
        }

        List<RoleInfo> roleInfoList = JSONObject.parseArray(req.get("roles").toString(), RoleInfo.class);

        userService.addRole(userId, roleInfoList);

        logUtil.out("请求给用户添加角色成功");
        return ResultInfo.success("添加成功");
    }

    // 删除角色
    @DeleteMapping("/user/role")
    @ApiOperation("给用户删除角色")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
    })
    public ResultInfo deleteRole(@RequestBody Map<String, Object> req) {
        logUtil.in("请求给用户删除角色");
        if (req.size() == 0 || !req.containsKey("userId") || !req.containsKey("roles")) {
            logUtil.out("请求给用户删除角色 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        int userId = (int) req.get("userId");
        if (!userService.existsById(userId)) {
            logUtil.out("请求给用户删除角色 用户未找到");
            return ResultInfo.error("用户未找到");
        }
        List<RoleInfo> roleInfoList = JSONObject.parseArray(req.get("roles").toString(), RoleInfo.class);

        userService.deleteRole(userId, roleInfoList);

        logUtil.out("请求给用户删除角色成功");
        return ResultInfo.success("删除成功");
    }
}
