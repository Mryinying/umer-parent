package com.umer.alihealth.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class CooperationOrderInfo implements Serializable {

    /*
     └ checkNo	String	否		机构checkNo
     └ certNumber	String	否		证件号
     └ phone	String	否		电话号码
     └ married	String	否		婚否(0-未婚; 1-已婚)
     └ certType	String	否		证件类型(0-身份证; 1-护照; 2-军官证)
     └ name	String	否		体检人姓名
     └ gender	String	否		性别(0-男;1-女;)
     └ checkDate	String	否		体检日期
     └ reportstatus	String	是		体检状态：未到检(exam_not), 已到检(exam_done)； 上门服务中还需以下两种状态：预约确认中（reserve_confirming），预约拒绝（reserve_rejected）；
     └ uniqReserveCode	String	是		机构的预约唯一码
     └ reserveNumber	String	否		阿里健康预约唯一标识
     └ cancel_reason	String	否		预约拒绝原因，在预约拒绝后才需赋值（仅上门服务中使用）
    */

    /**
     * 机构checkNo
     */
    private String checkNo;
    /**
     * 证件号
     */
    private String certNumber;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 婚否(0-未婚; 1-已婚)
     */
    private String married;
    /**
     * 证件类型(0-身份证; 1-护照; 2-军官证)
     */
    private String certType;
    /**
     * 体检人姓名
     */
    private String name;
    /**
     * 性别(0-男;1-女;)
     */
    private String gender;
    /**
     * 体检日期
     */
    private String checkDate;
    /**
     * 体检状态：未到检(exam_not), 已到检(exam_done)； 上门服务中还需以下两种状态：预约确认中（reserve_confirming），预约拒绝（reserve_rejected）；
     */
    private String reportstatus;
    /**
     * 机构的预约唯一码
     */
    private String uniqReserveCode;
    /**
     * 阿里健康预约唯一标识
     */
    private String reserveNumber;
    /**
     * 预约拒绝原因，在预约拒绝后才需赋值（仅上门服务中使用）
     */
    private String cancel_reason;
}
