package com.umer.alihealth.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.umer.alihealth.auth.TokenProperties;
import com.umer.alihealth.constants.Constants;
import com.umer.alihealth.service.UserService;
import com.umer.alihealth.utils.JwtUtils;
import com.umer.common.api.Result;
import com.umer.common.api.ResultCode;
import com.umer.common.service.RedisService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 登录认证检查过滤器
 */
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProperties tokenProperties;

    private final RedisService redisService;

    private final UserService userService;

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // 获取 认证头
        String authorizationHeader = request.getHeader(tokenProperties.getAuthorizationHeaderName());
        if (!checkIsTokenAuthorizationHeader(authorizationHeader)) {
            log.debug("获取到认证头Authorization的值:[{}]但不是我们系统中登录后签发的。", authorizationHeader);
            writeJson(response, "token不能为空");
            filterChain.doFilter(request, response);
            return;
        }
        // 获取到真实的token
        String realToken = getRealAuthorizationToken(authorizationHeader);
        String userId;
        // 解析 jwt token
        Jws<Claims> jws = null;
        try {
            jws = Jwts.parser().setSigningKey(tokenProperties.getSecretKey()).parseClaimsJws(realToken);
            userId = jws.getBody().getSubject();
        } catch (ExpiredJwtException e) {
            log.error(e.getMessage());
            userId = e.getClaims().getSubject();
        }
        // token 不合法
        if (null == userId) {
            writeJson(response, "认证token不合法");
            return;
        }

        //强制下线
        Object redisToken = redisService.get(Constants.RedisPrefix.TOKEN_PREFIX + userId);
        if(redisToken==null || !redisToken.equals(realToken)){
            if(redisToken!=null)
                response.addHeader(tokenProperties.getAuthorizationHeaderName(), redisToken.toString());
            writeJson(response, "认证token已过期，请重新登录");
            return;
        }
        // token过期 处理过期
        if(jws==null){
            handleTokenExpired(response, request, filterChain,userId);
        }

        // 构建认证对象
        saveAuthentication(response, request, filterChain, userId,realToken);
    }
    /**
     * 处理token过期情况
     *
     * @param response
     * @param request
     * @param filterChain
     * @param userId
     * @return
     * @throws IOException
     */
    private void handleTokenExpired(HttpServletResponse response, HttpServletRequest request, FilterChain filterChain, String userId) throws IOException, ServletException {

        // 重新签发 token
        String newToken = JwtUtils.generatorJwtToken(
                userId,
                tokenProperties.getTokenExpireSecond(),
                tokenProperties.getSecretKey()
        );

        redisService.del(Constants.RedisPrefix.TOKEN_PREFIX + userId);
        redisService.set(Constants.RedisPrefix.TOKEN_PREFIX + userId,newToken,tokenProperties.getTokenExpireSecond()*2);

        // 构建认证对象
        saveAuthentication(response, request, filterChain, userId,newToken);

    }

    private void saveAuthentication(HttpServletResponse response, HttpServletRequest request, FilterChain filterChain, Object userId,String token) throws IOException, ServletException {
        Set<String> permissions = userService.findPermissions(userId.toString());
        List<GrantedAuthority> authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(userId, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
        response.addHeader(tokenProperties.getAuthorizationHeaderName(), token);
        response.addHeader(tokenProperties.getAuthorities(), new Gson().toJson(authorities));
        filterChain.doFilter(request, response);
    }

    /**
     * 写 json 数据给前端
     *
     * @param response
     * @throws IOException
     */
    private void writeJson(HttpServletResponse response, String msg) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().print(OBJECT_MAPPER.writeValueAsString(Result.failed( ResultCode.FORBIDDEN.getCode(),msg)));
    }

    /**
     * 获取到真实的 token 串
     *
     * @param authorizationToken
     * @return
     */
    private String getRealAuthorizationToken(String authorizationToken) {
        return StringUtils.substring(authorizationToken, tokenProperties.getTokenHeaderPrefix().length()).trim();
    }

    /**
     * 判断是否是系统中登录后签发的token
     *
     * @param authorizationHeader
     * @return
     */
    private boolean checkIsTokenAuthorizationHeader(String authorizationHeader) {
        if (StringUtils.isBlank(authorizationHeader)) {
            return false;
        }
        return StringUtils.startsWith(authorizationHeader, tokenProperties.getTokenHeaderPrefix());
    }
}