package com.umer.alihealth.entity;

import lombok.Data;

/**
 * 用户模型
 * @author Louis
 * @date Jun 29, 2019
 */
@Data
public class User {

    private Long id;
    
    private String username;

    private String password;

}