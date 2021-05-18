package com.ls.erp.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.ls.erp.entity.UserInfo;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TokenUtil {

    public String getToken(UserInfo userInfo) {
        String token = "";
        token = JWT.create()
                // 存入token中的信息
                .withAudience(userInfo.getId()+"", new Date().getTime()+"")
                // 生成token，密钥是密码
                .sign(Algorithm.HMAC256(userInfo.getPassword()));
        return token;
    }

}
