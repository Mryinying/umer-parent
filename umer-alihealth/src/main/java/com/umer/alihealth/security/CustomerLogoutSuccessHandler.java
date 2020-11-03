package com.umer.alihealth.security;

import com.umer.alihealth.utils.JwtUtils;
import com.umer.common.api.Result;
import com.umer.common.constant.AuthConstant;
import com.umer.common.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出成功的调用类
 * @author zzzgd
 */
@Slf4j
@Component
public class CustomerLogoutSuccessHandler implements LogoutSuccessHandler {


    private final RedisService redisService;

    @Autowired
    private CustomerLogoutSuccessHandler(final RedisService redisService){
        this.redisService = redisService;
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        log.info("CustomerLogoutSuccessHandler============================");
        String authorizationHeader = request.getHeader(AuthConstant.JWT_TOKEN_HEADER);
        if(null==authorizationHeader){
            JwtUtils.writeJson(response, Result.failed("未登录"), HttpStatus.UNAUTHORIZED);
            return;
        }
        redisService.del(JwtUtils.getToken(request));
        //设置response状态码，返回错误信息等
        JwtUtils.writeJson(response, Result.failed("login out"),HttpStatus.UNAUTHORIZED);
    }
}

