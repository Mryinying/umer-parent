package com.umer.alihealth.security;

import com.umer.alihealth.utils.JwtUtils;
import com.umer.common.api.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 匿名未登录的时候访问,需要登录的资源的调用类
 */
@Slf4j
@Component
public class CustomerAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        //设置response状态码，返回错误信息等
        log.info("CustomerAuthenticationEntryPoint========================");
        JwtUtils.writeJson(httpServletResponse, Result.failed("认证token不合法"), HttpStatus.UNAUTHORIZED);
    }
}


