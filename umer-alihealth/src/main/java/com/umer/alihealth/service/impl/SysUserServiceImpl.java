package com.umer.alihealth.service.impl;

import com.umer.alihealth.entity.User;
import com.umer.alihealth.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class SysUserServiceImpl implements UserService {

	@Override
	public User findByUsername(String username) {
		User user = new User();
		user.setId(1L);
		user.setUsername(username);
		String password = new BCryptPasswordEncoder().encode("123");
		user.setPassword(password);
		return user;
	}

	@Override
	public Set<String> findPermissions(String username) {
		Set<String> permissions = new HashSet<>();
		permissions.add("sys:user:view");
		permissions.add("sys:user:add");
		permissions.add("sys:user:edit");
		permissions.add("sys:user:delete");
		return permissions;
	}

	@Override
	public Set<String> findRole(String username) {
		Set<String> roles = new HashSet<>();
		if(username.length()>5)
			roles.add("admin");
		else
			roles.add("emp");
		return roles;
	}

}
