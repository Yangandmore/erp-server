package com.ls.erp.exception;

import com.ls.erp.entity.ResultInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResultInfo handleException(Exception e) {
        log.error("<----请求过程异常-Exception");
        e.printStackTrace();
        return ResultInfo.error();
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(TokenException.class)
    public ResultInfo handleException(TokenException e) {
        log.error("<----验证过程异常-TokenException");
        e.printStackTrace();
        return ResultInfo.error(-2, "token失效");
    }
}
