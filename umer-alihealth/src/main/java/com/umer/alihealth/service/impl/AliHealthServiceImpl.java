package com.umer.alihealth.service.impl;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAlihealthExaminationHospitalOnofflineRequest;
import com.taobao.api.response.AlibabaAlihealthExaminationHospitalOnofflineResponse;
import com.umer.alihealth.entity.CooperationOrderInfo;
import com.umer.alihealth.service.AliHealthService;
import com.umer.alihealth.vo.AliResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AliHealthServiceImpl implements AliHealthService {

    @Value("${alihealth.appkey}")
    private String appkey;
    @Value("${alihealth.url}")
    private String url;
    @Value("${alihealth.secret}")
    private String secret;

    @Override
    public void queryStock() throws ApiException {
        TaobaoClient client = new DefaultTaobaoClient(url, appkey, secret);
        AlibabaAlihealthExaminationHospitalOnofflineRequest req = new AlibabaAlihealthExaminationHospitalOnofflineRequest();
        req.setHospitalCodes("1,2,3");
        req.setType("online");
        AlibabaAlihealthExaminationHospitalOnofflineResponse rsp = client.execute(req);
        System.out.println(rsp.getBody());

    }

    @Override
    public void modifyReserve() {

    }

    @Override
    public void cancelReserve() {

    }

    @Override
    public void confirmReserve() {

    }

    @Override
    public AliResult<CooperationOrderInfo> stateReserve(HttpServletRequest request, String merchantCode, String reserveNumber, String uniqReserveCode) {
        return AliResult.success( new CooperationOrderInfo());
    }

    @Override
    public void reportReserve() {

    }
}
