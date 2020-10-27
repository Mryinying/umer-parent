package com.umer.alihealth.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJws;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * jwt 工具类
 *
 * @author huan
 * @date 2020-05-20 - 17:09
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {

    /**
     * 解析 jwt token
     *
     * @param token     需要解析的json
     * @param secretKey 密钥
     * @return
     */
    public static Jws<Claims> parserAuthenticateToken(String token, String secretKey) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            return new DefaultJws<>(null, e.getClaims(), "");
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException | IncorrectClaimException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 判断 jwt 是否过期
     *
     * @param jws
     * @return true:过期 false:没过期
     */
    public static boolean isJwtExpired(Jws<Claims> jws) {
        return jws.getBody().getExpiration().before(new Date());
    }

    /**
     * 构建认证过的认证对象
     */
    public static void buildAuthentication(Jws<Claims> jws, String userIdFieldName, List<GrantedAuthority> authorities) {
        Object userId = jws.getBody().get(userIdFieldName);
        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(userId, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(testingAuthenticationToken);
    }

    /**
     * 生成 jwt token
     */
    public static String generatorJwtToken(Object loginUserId, Long expireSecond, String secretKey) {
        Date expireTime = Date.from(LocalDateTime.now().plusSeconds(expireSecond).atZone(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuedAt(new Date())
                .setExpiration(expireTime)
                .setSubject(loginUserId.toString())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
