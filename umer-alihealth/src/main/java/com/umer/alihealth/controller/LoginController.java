package com.umer.alihealth.controller;

import com.umer.alihealth.entity.User;
import com.umer.alihealth.security.JwtAuthenticatioToken;
import com.umer.alihealth.utils.SecurityUtils;
import com.umer.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 登录控制器
 * @author Louis
 * @date Jun 29, 2019
 */
@RestController
public class LoginController {

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 登录接口
     */
    @PostMapping(value = "/login")
    public Result login(@RequestBody User loginBean, HttpServletRequest request) throws IOException {
        String username = loginBean.getUsername();
        String password = loginBean.getPassword();
        
        // 系统登录认证
        JwtAuthenticatioToken token = SecurityUtils.login(request, username, password, authenticationManager);
                
        return Result.success(token);
    }

}