package com.ls.erp.controller;

import com.ls.erp.entity.ResultInfo;
import com.ls.erp.entity.UserInfo;
import com.ls.erp.service.UserService;
import com.ls.erp.utils.LogUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "用户管理接口")
public class UserController {

    @Resource
    LogUtil logUtil;

    @Autowired
    private UserService userService;

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



        logUtil.out("请求添加用户成功");
        return ResultInfo.success("添加成功");
    }

    // 删除用户

    // 编辑用户

    // 查询用户
    @GetMapping("/user/all")
    @ApiOperation("查询所有用户")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求失败", response = ResultInfo.class),
    })
    public ResultInfo findAllUser() {
        logUtil.in("请求查询所有用户");
//        List<UserInfo> userInfoList = userService.findAllUser();
        logUtil.out("请求查询所有用户成功");
        return ResultInfo.success(null, "查询成功");
    }

}
