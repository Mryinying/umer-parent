package com.umer.alihealth.controller;

import com.taobao.api.ApiException;
import com.taobao.api.internal.spi.CheckResult;
import com.taobao.api.internal.spi.SpiUtils;
import com.umer.alihealth.entity.CooperationOrderInfo;
import com.umer.alihealth.service.AliHealthService;
import com.umer.alihealth.vo.AliResult;
import com.umer.common.api.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户控制器
 * @author Louis
 * @date Jun 29, 2019
 */
@RestController
@RequestMapping("/api")
public class AliHealthController {

    @Autowired
    private AliHealthService aliHealthService;

    @Value("${alihealth.secret}")
    private String secret;

    @Value("${alihealth.appkey}")
    private String appkey;

    @ModelAttribute
    public void signCheck(HttpServletRequest request,String app_key, String method, String timestamp, String sign) throws ApiException {
        CheckResult checkResult = null;
        try {
            checkResult = SpiUtils.checkSign(request, secret);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException("500","check fail");
        }
        if(app_key.equals(appkey) && checkResult.isSuccess()){
            return;
        }
        throw new ApiException("500","check fail");
    }

    @GetMapping(value="/stock/query")
    public Result findAll() {
        return Result.success(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
    }

    /**
     *
     * @param app_key 是	20	TOP分配给应用的Appkey
     * @param method 是	40	API接口名称
     * @param timestamp 是	40	时间戳，格式：yyyy-MM-dd HH:mm:ss
     * @param sign 是	32	请求签名，遵守TOP签名规范
     * @param merchantCode 是		阿里健康与体检机构合作商户编码
     * @param reserveNumber 是		预约唯一标识
     * @param uniqReserveCode 是		体检机构预约唯一标识码
     * @return http://localhost:8080/alihealth/state/query
     */
    @PostMapping(value="/state/query")
    public AliResult<CooperationOrderInfo> queryState(String app_key,
                                                      String method,
                                                      String timestamp,
                                                      String sign,
                                                      String merchantCode,
                                                      String reserveNumber,
                                                      String uniqReserveCode,
                                                      HttpServletRequest request
                             ) {

        return aliHealthService.stateReserve(request,merchantCode,reserveNumber,uniqReserveCode);
    }

}