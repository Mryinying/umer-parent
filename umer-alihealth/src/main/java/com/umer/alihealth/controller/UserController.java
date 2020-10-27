package com.umer.alihealth.controller;

import com.umer.alihealth.auth.TokenProperties;
import com.umer.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户控制器
 * @author Louis
 * @date Jun 29, 2019
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private TokenProperties tokenProperties;

    @PreAuthorize("hasAuthority('sys:user:sss')")
    @GetMapping(value="/findAll")
    public Result findAll() {
        String s = tokenProperties.toString();
        SecurityContextHolder.getContext().getAuthentication();
        return Result.success(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    }
    
    @PreAuthorize("hasAuthority('sys:user:edit')")
    @GetMapping(value="/edit")
    public Result edit() {
        return Result.success("the edit service is called success.");
    }
    
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @GetMapping(value="/delete")
    public Result delete() {
        return Result.success("the delete service is called success.");
    }

}