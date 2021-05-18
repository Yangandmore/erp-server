package com.ls.erp.controller;

import com.alibaba.fastjson.JSONObject;
import com.ls.erp.annotation.PassAuthToken;
import com.ls.erp.annotation.PassReturnToken;
import com.ls.erp.entity.ResultInfo;
import com.ls.erp.entity.RoleInfo;
import com.ls.erp.entity.UserInfo;
import com.ls.erp.service.UserService;
import com.ls.erp.utils.LogUtil;
import com.ls.erp.utils.TokenUtil;
import com.mysql.cj.core.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@Api(tags = "用户管理接口")
public class UserController {

    @Resource
    LogUtil logUtil;

    @Resource
    TokenUtil tokenUtil;

    @Autowired
    private UserService userService;

    // 用户登陆
    @PassAuthToken
    @PostMapping("/user/login")
    @ApiOperation("登陆用户")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
    })
    public ResultInfo loginUser(UserInfo userInfo, HttpServletResponse response) {
        logUtil.in("请求登陆用户");
        if (userInfo == null || StringUtils.isNullOrEmpty(userInfo.getLoginName()) || StringUtils.isNullOrEmpty(userInfo.getPassword())) {
            logUtil.out("请求登陆用户 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        UserInfo u = userService.findUserByLoginNameAndPassword(userInfo.getLoginName(), userInfo.getPassword());
        if (u == null) {
            logUtil.in("请求登陆用户失败 未找到该用户");
            return ResultInfo.error("未找到该用户");
        }

        logUtil.in("请求登陆用户成功");
        response.setHeader("token", tokenUtil.getToken(u));
        return ResultInfo.success("登陆成功");
    }

    // 用户注册
    @PassAuthToken
    @PassReturnToken
    @PostMapping("/user/sign")
    @ApiOperation("注册用户")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
    })
    public ResultInfo signUser(UserInfo userInfo) {
        logUtil.in("请求注册用户");
        if (userInfo == null || StringUtils.isNullOrEmpty(userInfo.getLoginName()) || StringUtils.isNullOrEmpty(userInfo.getPassword()) || StringUtils.isNullOrEmpty(userInfo.getName())) {
            logUtil.out("请求注册用户 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }

        if (userService.existsLoginName(userInfo.getLoginName())) {
            logUtil.out("请求注册用户 用户已存在");
            return ResultInfo.error("用户已存在");
        }

        userService.addUser(userInfo);

        logUtil.in("请求注册用户成功");
        return ResultInfo.success("注册成功");
    }

    // 新增用户
    @PostMapping("/user")
    @ApiOperation("添加用户")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
    })
    public ResultInfo addUser(UserInfo userInfo) {
        logUtil.in("请求添加用户");

        if (userInfo == null || StringUtils.isNullOrEmpty(userInfo.getLoginName()) || StringUtils.isNullOrEmpty(userInfo.getPassword()) || StringUtils.isNullOrEmpty(userInfo.getName())) {
            logUtil.out("请求添加用户 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        if (userService.existsLoginName(userInfo.getLoginName())) {
            logUtil.out("请求注册用户 用户已存在");
            return ResultInfo.error("用户已存在");
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
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
    })
    public ResultInfo deleteUser(UserInfo userInfo) {
        logUtil.in("请求删除用户");

        if (userInfo == null) {
            logUtil.out("请求删除角色 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        if (!userService.existsById(userInfo.getId())) {
            logUtil.out("请求注册用户 用户不存在");
            return ResultInfo.error("用户不存在");
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
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
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
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
    })
    public ResultInfo findAllUser() {
        logUtil.in("请求查询所有用户");
        List<UserInfo> userInfoList = userService.findAllUser();
        logUtil.out("请求查询所有用户成功");
        return ResultInfo.success(userInfoList, "查询成功");
    }

    @GetMapping("/user/find/{id}")
    @ApiOperation("根据id查看用户")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
    })
    public ResultInfo findUserById(@PathVariable("id")int id) {
        logUtil.in("请求查询ID用户");
        if (id == 0) {
            // id 错误
            logUtil.out("请求查询ID用户 接收参数为空");
            return ResultInfo.error("请求参数不正确");
        }
        UserInfo userInfo = userService.findUserById(id);
        if (userInfo == null) {
            logUtil.out("请求查询ID用户 未找到");
            return ResultInfo.error("未找到");
        }

        logUtil.out("请求查询ID用户 成功");
        return ResultInfo.success(userInfo, "查找成功");
    }

    // 查询相关用户的角色
    @GetMapping("/user/role/{id}")
    @ApiOperation("查看用户角色")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
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
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
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
            @ApiResponse(code = -2, message = "token失效", response = ResultInfo.class),
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
