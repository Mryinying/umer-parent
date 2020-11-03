package com.umer.alihealth.constants;

/**
 * 常量信息
 */
public interface Constants {

    /**
     * 日期格式化Pattern
     */
    interface DateType {
        String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
        String DATE = "yyyy-MM-dd";
        String TIME = "HH:mm:ss";
        String YEAR_MONTH = "yyyy-MM";
    }

    interface RedisPrefix{
        String TOKEN_PREFIX = "umer:token:alihealth:";
    }

    interface AliHealthApi{
        //1.体检机构对接_体检套餐库存查询：
        String STOCK_QUERY = "alibaba.alihealth.examination.stock.query";
        //2.体检机构对接_体检套餐改期：
        String RESERVE_MODIFY = "alibaba.alihealth.examination.reserve.modify";
        //3.体检机构对接_体检取消：
        String RESERVE_CANCEL = "alibaba.alihealth.examination.reserve.cancel";
        //4.向体检机构确认用户购买的体检套餐信息：
        String RESERVE_CONFIRM = "alibaba.alihealth.examination.reserve.confirm";
        //5.体检机构对接_体检状态查询：
        String RESERVE_STATE = "alibaba.alihealth.examination.reserve.state";
        //6.体检机构对接_体检报告获取：
        String RESERVE_REPORT = "alibaba.alihealth.examination.reserve.report";

        //        1.体检机构对接_商品发布/更新：
        //        注意：*测试商品名称需要以"测试商品下单无效"为开头,便可自动审核（自动审核在第六步、测试阶段中）;
        //            *测试阶段label字段一定要传B！（否则会在线上透出）;
        //            *测试商品的价格不要高于150元，否则会影响自动审核流程;
        String GOODS_PUBLISH = "alibaba.alihealth.examination.goods.publish";
        //        2.体检机构对接_门店发布／更新：
        //        注意：*测试商品名称需要以"测试门店下单无效"为开头,便可自动审核（自动审核在第六步、测试阶段中）
        String HOSPITAL_PUBLISH = "alibaba.alihealth.examination.hospital.publish";
        //        3.体检机构对接_上线/下线 体检产品：
        //        注意：*测试商品和测试门店在第六步自动审核后，需要通过此接口将商品和门店进行关联上线
        String GOODS_ONOFFLINE = "alibaba.alihealth.examination.goods.onoffline";
    }
}
