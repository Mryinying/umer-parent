package com.umer.alihealth.config;

import com.umer.common.util.GsonUtils;
import eu.bitwalker.useragentutils.UserAgent;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * 添加aop日志打印
 *
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    /**
     * 定义请求日志切入点，其切入点表达式有多种匹配方式,这里是指定路径
     */
    @Pointcut("execution(public * com.umer.alihealth.controller.*.*(..))")
    public void webLogPointcut() {
    }

    /**
     * 在执行方法前后调用Advice，这是最常用的方法，相当于@Before和@AfterReturning全部做的事儿
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("webLogPointcut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //获取请求头中的User-Agent
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        //打印请求的内容
        long startTime = System.currentTimeMillis();
        //log.info("请求Url : {}" , request.getRequestURL().toString());
        //log.info("请求方式 : {}" , request.getMethod());
        //log.info("请求ip : {}" , request.getRemoteAddr());
        //log.info("请求方法 : {}" , pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());
        //log.info("请求参数 : {}" , Arrays.toString(pjp.getArgs()));
        //// 系统信息
        //log.info("浏览器：{}", userAgent.getBrowser().toString());
        //log.info("浏览器版本：{}",userAgent.getBrowserVersion());
        //log.info("操作系统: {}", userAgent.getOperatingSystem().toString());
        // pjp.proceed()：当我们执行完切面代码之后，还有继续处理业务相关的代码。proceed()方法会继续执行业务代码，并且其返回值，就是业务处理完成之后的返回值。
        Object ret = pjp.proceed();
        //log.info("请求结束时间："+ LocalDateTime.now());
        //log.info("请求耗时：{}" , (System.currentTimeMillis() - startTime));
        //// 处理完请求，返回内容
        //log.info("请求返回 : {}" , ret);
        LogVO logVO = new LogVO().setUrl(request.getRequestURL().toString())
                .setMethod(request.getMethod())
                .setIp(request.getRemoteAddr())
                .setSignature(pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName())
                .setParam(Arrays.toString(pjp.getArgs()))
                .setExplorer(userAgent.getBrowser().toString() + userAgent.getBrowserVersion())
                .setOs(userAgent.getOperatingSystem().toString())
                .setTime((System.currentTimeMillis() - startTime))
                .setResult(ret.toString());
        log.info(GsonUtils.toJsonString(logVO));
        return ret;
    }
    @Data
    @Accessors(chain = true)
    static class LogVO{
        private String url;
        private String signature;
        private String ip;
        private String method;
        private String param;
        private String explorer;
        private String os;
        private long time;
        private String result;
    }
}
