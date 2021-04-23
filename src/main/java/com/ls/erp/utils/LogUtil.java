package com.ls.erp.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogUtil {

    public void in(String msg) {
        log.info("----> " + msg);
    }

    public void out(String msg) {
        log.info("<---- " + msg);
    }

    public void outS() {
        log.info("<---- 请求成功");
    }
}
