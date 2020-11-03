package com.umer.alihealth.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umer.common.api.Result;
import com.umer.common.constant.AuthConstant;
import com.umer.common.util.GsonUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * jwt 工具类
 *
 * @author huan
 * @date 2020-05-20 - 17:09
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
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

    public static String getToken(HttpServletRequest request){
        String authorizationHeader = request.getHeader(AuthConstant.JWT_TOKEN_HEADER);
        return StringUtils.substring(authorizationHeader,AuthConstant.JWT_TOKEN_PREFIX.length()).trim();
    }

    /**
     * 写 json 数据给前端
     *
     * @param response
     * @throws IOException
     */
    public static void writeJson(HttpServletResponse response, Result msg,HttpStatus httpStatus) throws IOException {
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(httpStatus.value());
        response.getWriter().print(GsonUtils.toJsonString(msg));
    }
}
