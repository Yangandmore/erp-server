package com.ls.erp.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "统一返回数据模版")
@Data
public class ResultInfo {
    @ApiModelProperty(value = "请求状态码")
    private int status;

    @ApiModelProperty(value = "请求信息")
    private String msg;

    @ApiModelProperty(value = "返回数据")
    private Object data;

    public static ResultInfo success() {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setStatus(0);
        return resultInfo;
    }

    public static ResultInfo success(String msg) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setStatus(0);
        resultInfo.setMsg(msg);
        return resultInfo;
    }

    public static ResultInfo success(Object data, String msg) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setStatus(0);
        resultInfo.setData(data);
        resultInfo.setMsg(msg);
        return resultInfo;
    }

    public static ResultInfo error() {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setStatus(-1);
        resultInfo.setMsg("服务异常");
        return resultInfo;
    }

    public static ResultInfo error(int code, String msg) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setStatus(code);
        resultInfo.setMsg(msg);
        return resultInfo;
    }

    public static ResultInfo error(String msg) {
        ResultInfo resultInfo = new ResultInfo();
        resultInfo.setStatus(-1);
        resultInfo.setMsg(msg);
        return resultInfo;
    }
}
