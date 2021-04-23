package com.ls.erp.controller;

import com.ls.erp.entity.ResultInfo;
import com.ls.erp.entity.UserInfo;
import com.ls.erp.service.UserServer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "用户管理接口")
public class UserController {

    @Autowired
    private UserServer userServer;

    // 新增用户

    // 删除用户

    // 编辑用户

    // 查询用户
    @GetMapping("/user/all")
    @ApiOperation("查询所有文件夹信息")
    @ApiResponses({
            @ApiResponse(code = 0, message = "请求成功", response = ResultInfo.class),
            @ApiResponse(code = -1, message = "请求异常", response = ResultInfo.class),
    })
    public ResultInfo findAllUser() {
        List<UserInfo> userInfoList = userServer.findAllUser();
        return ResultInfo.success(userInfoList, "请求成功");
    }

}
