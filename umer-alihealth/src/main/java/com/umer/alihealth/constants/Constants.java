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
}
