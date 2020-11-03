package com.umer.alihealth.security;

import com.google.gson.Gson;
import com.umer.alihealth.auth.TokenProperties;
import com.umer.alihealth.constants.Constants;
import com.umer.alihealth.service.UserService;
import com.umer.alihealth.utils.JwtUtils;
import com.umer.common.api.Result;
import com.umer.common.constant.AuthConstant;
import com.umer.common.service.RedisService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        // 获取 认证头
        String authorizationHeader = request.getHeader(AuthConstant.JWT_TOKEN_HEADER);
        if (!checkIsTokenAuthorizationHeader(authorizationHeader)) {
            filterChain.doFilter(request, response);
            return;
        }
        // 获取到真实的token
        String realToken = getRealAuthorizationToken(authorizationHeader);
        String userId;
        String status = "0";
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
            log.info("未登录或token失效 ===================");
            JwtUtils.writeJson(response, Result.failed("未登录或token失效，请重新登录"), HttpStatus.UNAUTHORIZED);
            return;
        }

        //强制下线
        Object redisToken = redisService.get(Constants.RedisPrefix.TOKEN_PREFIX + userId);
        if (redisToken == null || !redisToken.equals(realToken)) {
            if (redisToken != null)
                response.addHeader(AuthConstant.JWT_TOKEN_HEADER, redisToken.toString());
            log.info("认证token已过期 ===================userId{}", userId);
            JwtUtils.writeJson(response, Result.failed("认证token已过期，请重新登录"), HttpStatus.UNAUTHORIZED);
            return;
        }
        // token刷新策略
        //1.token过期失效刷新
        //2.过期时间过3/4刷新
        Date expiration = Jwts.parser().setSigningKey(tokenProperties.getSecretKey()).parseClaimsJws(realToken).getBody().getExpiration();
        if (jws == null || expiration.toInstant().minusSeconds((long) (tokenProperties.getTokenExpireSecond() * 0.75)).isBefore(new Date().toInstant())) {
            // 重新签发 token
            String newToken = JwtUtils.generatorJwtToken(userId, tokenProperties.getTokenExpireSecond(), tokenProperties.getSecretKey());
            redisService.del(Constants.RedisPrefix.TOKEN_PREFIX + userId);
            redisService.set(Constants.RedisPrefix.TOKEN_PREFIX + userId, newToken, tokenProperties.getTokenExpireSecond() * 2);
            realToken = newToken;
            status = "1";
        }

        // 构建认证对象
        saveAuthentication(response, userId, realToken, status);

        filterChain.doFilter(request, response);
    }

    private void saveAuthentication(HttpServletResponse response, Object userId, String token, String status) {
        Set<String> permissions = userService.findPermissions(userId.toString());
        List<GrantedAuthority> authorities = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(userId, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
        response.addHeader(AuthConstant.JWT_TOKEN_HEADER, token);
        response.addHeader(AuthConstant.JWT_TOKEN_STATUS, status);
        response.addHeader(AuthConstant.AUTHORITY_CLAIM_NAME, new Gson().toJson(authorities));
    }

    /**
     * 获取到真实的 token 串
     *
     * @param authorizationToken
     * @return
     */
    private String getRealAuthorizationToken(String authorizationToken) {
        return StringUtils.substring(authorizationToken, AuthConstant.JWT_TOKEN_PREFIX.length()).trim();
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
        return StringUtils.startsWith(authorizationHeader, AuthConstant.JWT_TOKEN_PREFIX);
    }
}