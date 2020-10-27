package com.umer.alihealth.controller;

import com.umer.alihealth.auth.TokenProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录控制器
 * @author Louis
 * @date Jun 29, 2019
 */
@RestController(value = "/umer")
public class LoginController {
    @Autowired
    private TokenProperties tokenProperties;

}