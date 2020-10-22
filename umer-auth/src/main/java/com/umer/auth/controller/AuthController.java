package com.umer.auth.controller;

import com.umer.auth.domain.Oauth2TokenDto;
import com.umer.common.api.Result;
import com.umer.common.constant.AuthConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Map;

/**
 * 自定义Oauth2获取令牌接口
 */
@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Autowired
    private TokenEndpoint tokenEndpoint;

    /**
    @ApiImplicitParam(name = "grant_type", value = "授权模式", required = true),
    @ApiImplicitParam(name = "client_id", value = "Oauth2客户端ID", required = true),
    @ApiImplicitParam(name = "client_secret", value = "Oauth2客户端秘钥", required = true),
    @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
    @ApiImplicitParam(name = "username", value = "登录用户名"),
    @ApiImplicitParam(name = "password", value = "登录密码")*/
    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public Result<Oauth2TokenDto> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException {
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, parameters).getBody();
        Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead(AuthConstant.JWT_TOKEN_PREFIX).build();

        return Result.success(oauth2TokenDto);
    }
}
