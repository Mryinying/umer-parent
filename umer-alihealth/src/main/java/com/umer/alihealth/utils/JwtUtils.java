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
