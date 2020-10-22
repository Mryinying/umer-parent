package com.umer.auth.service;

import com.umer.auth.domain.UserDto;
/**
 * Created by macro on 2020/7/16.
 */
public interface UmsMemberService {

    UserDto loadUserByUsername( String username);
}
