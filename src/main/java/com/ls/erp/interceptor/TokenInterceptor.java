package com.ls.erp.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ls.erp.annotation.PassAuthToken;
import com.ls.erp.annotation.PassReturnToken;
import com.ls.erp.entity.UserInfo;
import com.ls.erp.exception.TokenException;
import com.ls.erp.service.UserService;
import com.ls.erp.utils.LogUtil;
import com.ls.erp.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

@Service
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Resource
    TokenUtil tokenUtil;

    @Resource
    LogUtil logUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();
        // 检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassAuthToken.class)) {
            return true;
        }
        // 检测是否需要pass udate toekn
        boolean updateToken = true;
        if (method.isAnnotationPresent(PassReturnToken.class)) {
            updateToken = false;
        }
        logUtil.in("开始验证token");
        // 开始认证token
        if (token == null) {
            throw new TokenException("未找到token，请重新登录");
        }
        // 获取 token 中的 user id
        String userId;
        try {
            userId = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            throw new TokenException("token验证异常");
        }
        UserInfo userInfo = userService.findUserById(Integer.parseInt(userId));
        if (userInfo == null) {
            throw new TokenException("token验证异常");
        }

        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(userInfo.getPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new TokenException("token验证异常");
        }

        // 查看时间状况
        String time;
        try {
            time = JWT.decode(token).getAudience().get(1);
            if (new Date().getTime() - Long.parseLong(time) > 1000 * 60 * 60 * 24) {
                // 超时处理
                throw new TokenException("token数据超时,请重新登陆");
            }
        } catch (JWTDecodeException j) {
            throw new TokenException("token验证异常");
        }
        logUtil.out("Token验证成功");

        // 检测是否需要pass udate toekn
        if (updateToken) {
            response.setHeader("token", tokenUtil.getToken(userInfo));
        }

        return true;
    }
}
