package com.umer.auth.service;

import com.umer.auth.domain.UserDto;

/**
 * Created by macro on 2019/10/18.
 */
public interface UmsAdminService {

    UserDto loadUserByUsername(String username);
}
