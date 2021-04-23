package com.ls.erp.filter;

import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CorsFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        // 允许http://www.xxx.com域（自行设置，这里只做示例）发起跨域请求
        res.addHeader("Access-Control-Allow-Origin", "*");
        // 设置允许跨域请求的方法
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        // 允许跨域请求包含content-type
        res.addHeader("Access-Control-Allow-Headers", "Content-Type,token");
        res.addHeader("Access-Control-Expose-Headers", "Content-Type,token");
        if (((HttpServletRequest) servletRequest).getMethod().equals("OPTIONS")) {
            servletResponse.getWriter().println("ok");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);

    }
}
