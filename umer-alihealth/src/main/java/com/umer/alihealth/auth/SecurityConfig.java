package com.umer.alihealth.auth;

import com.umer.alihealth.security.*;
import com.umer.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProperties tokenProperties;

    @Autowired
    private RedisService redisService;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomerLogoutSuccessHandler customerLogoutSuccessHandler;

    @Autowired
    private CustomerAuthenticationEntryPoint customerAuthenticationEntryPoint;

    @Autowired
    private CustomerRestAccessDeniedHandler customerRestAccessDeniedHandler;

    @Override
    public void configure(AuthenticationManagerBuilder auth) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        // 使用自定义登录身份认证组件
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        // 禁用 csrf, 由于使用的是JWT，我们这里不需要csrf
//        http.cors().and().csrf().disable()
//                .authorizeRequests()
//                // 跨域预检请求
//                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//                // 登录URL
//                .antMatchers("/login").permitAll()
//                // swagger
//                .antMatchers("/swagger**/**").permitAll()
//                .antMatchers("/webjars/**").permitAll()
//                .antMatchers("/v2/**").permitAll()
//                // 其他所有请求需要身份认证
//                .anyRequest().authenticated();
//        // 退出登录处理器
//        http.logout().logoutSuccessHandler(customerLogoutSuccessHandler);
//        // 开启登录认证流程过滤器
//        http.addFilterBefore(new JwtLoginFilter(authenticationManager(), tokenProperties, redisService), UsernamePasswordAuthenticationFilter.class);
//        // 访问控制时登录状态检查过滤器
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        /**
         * antMatchers: ant的通配符规则
         * ?	匹配任何单字符
         * *	匹配0或者任意数量的字符，不包含"/"
         * **	匹配0或者更多的目录，包含"/"
         */
        http
                .headers()
                .frameOptions().disable();

        http
                //登录后,访问没有权限处理类
                .exceptionHandling().accessDeniedHandler(customerRestAccessDeniedHandler)
                //匿名访问,没有权限的处理类
                .authenticationEntryPoint(customerAuthenticationEntryPoint)
        ;

        //使用jwt的Authentication,来解析过来的请求是否有token
        http
                .addFilterBefore(new JwtLoginFilter(authenticationManager(), tokenProperties, redisService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .authorizeRequests()
                //这里表示"/any"和"/ignore"不需要权限校验
                // 跨域预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 登录URL
                .antMatchers("/login").permitAll()
                // swagger
                .antMatchers("/swagger**/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/v2/**").permitAll()
                .anyRequest().authenticated()
                // 这里表示任何请求都需要校验认证(上面配置的放行)


                .and()
                //配置登录,检测到用户未登录时跳转的url地址,登录放行
                .formLogin()
                //需要跟前端表单的action地址一致
//                .loginProcessingUrl("/login")
                .permitAll()

                //配置取消session管理,又Jwt来获取用户状态,否则即使token无效,也会有session信息,依旧判断用户为登录状态
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                //配置登出,登出放行
                .and()
                .logout()
                .logoutSuccessHandler(customerLogoutSuccessHandler)
                .permitAll()

                .and()
                .csrf().disable()
        ;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
}
