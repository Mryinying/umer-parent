package com.umer.alihealth.security;

import com.umer.alihealth.utils.JwtUtils;
import com.umer.common.api.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 没有权限,被拒绝访问时的调用类
 */
@Component
@Slf4j
public class CustomerRestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        //设置response状态码，返回错误信息等
        JwtUtils.writeJson(response, Result.failed("no auth"), HttpStatus.UNAUTHORIZED);
    }

}

