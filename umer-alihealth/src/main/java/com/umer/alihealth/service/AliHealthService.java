package com.umer.alihealth.service;

import com.taobao.api.ApiException;
import com.umer.alihealth.entity.CooperationOrderInfo;
import com.umer.alihealth.vo.AliResult;

import javax.servlet.http.HttpServletRequest;

public interface AliHealthService {

    void queryStock() throws ApiException;

    void modifyReserve();

    void cancelReserve();

    void confirmReserve();

    AliResult<CooperationOrderInfo> stateReserve(HttpServletRequest request, String merchantCode, String reserveNumber, String uniqReserveCode);

    void reportReserve();
}
