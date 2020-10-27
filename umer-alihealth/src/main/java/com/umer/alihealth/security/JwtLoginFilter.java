package com.umer.alihealth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.umer.alihealth.auth.TokenProperties;
import com.umer.alihealth.entity.User;
import com.umer.common.service.RedisService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

/**
 * 启动登录认证流程过滤器
 */
@Slf4j
@Component
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

	private final TokenProperties tokenProperties;

	private final RedisService redisService;

	@Autowired
	public JwtLoginFilter(AuthenticationManager authManager,final TokenProperties tokenProperties,final RedisService redisService) {
		this.tokenProperties = tokenProperties;
		this.redisService = redisService;
		setAuthenticationManager(authManager);
    }

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// POST 请求 /login 登录时拦截， 由此方法触发执行登录认证流程，可以在此覆写整个登录认证逻辑
		super.doFilter(req, res, chain);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		// 可以在此覆写尝试进行登录认证的逻辑，登录成功之后等操作不再此方法内
		// 如果使用此过滤器来触发登录认证流程，注意登录请求数据格式的问题
		// 此过滤器的用户名密码默认从request.getParameter()获取，但是这种
		// 读取方式不能读取到如 application/json 等 post 请求数据，需要把
		// 用户名密码的读取逻辑修改为到流中读取request.getInputStream()
		User loginUser = null;
		try {
			loginUser = new ObjectMapper().readValue(request.getInputStream(), User.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String username = loginUser != null ? loginUser.getUsername().trim() : "";
		String password = loginUser != null ? loginUser.getPassword() : "";

		return this.getAuthenticationManager().authenticate(
				new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>())
		);

	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) {
		// 存储登录认证信息到上下文
		SecurityContextHolder.getContext().setAuthentication(authResult);
		// 触发事件监听器
		if (this.eventPublisher != null) {
			eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
		}

		// 生成并返回token给客户端，后续访问携带此token
		//header中返回用户权限信息
		Date expireTime = Date.from(LocalDateTime.now().plusSeconds(tokenProperties.getTokenExpireSecond()).atZone(ZoneId.systemDefault()).toInstant());
		String token = Jwts.builder()
				.setIssuedAt(new Date())
				.setSubject( ((UserDetails)authResult.getPrincipal()).getUsername())
				.setExpiration(expireTime)
				.signWith(SignatureAlgorithm.HS512, tokenProperties.getSecretKey())
				.compact();
		String username = ((UserDetails) authResult.getPrincipal()).getUsername();
		redisService.del(username);
		redisService.set(username,token,tokenProperties.getTokenExpireSecond()*2);

		response.addHeader(tokenProperties.getAuthorities(),new Gson().toJson(authResult.getAuthorities()));
		response.addHeader(tokenProperties.getAuthorizationHeaderName(),token);

	}

}
