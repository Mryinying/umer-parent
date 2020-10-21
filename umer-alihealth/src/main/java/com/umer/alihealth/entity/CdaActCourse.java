package com.umer.alihealth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * cda2020大会日程表
 * </p>
 *
 * @author zephyr
 * @since 2020-10-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class CdaActCourse extends Model<CdaActCourse> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 课程名
     */
    private String title;

    /**
     * 专家
     */
    private String expert;

    /**
     * 课程日期
     */
    private LocalDate courseDate;

    /**
     * 课程开始时间
     */
    private LocalTime startTime;

    /**
     * 课程结束时间
     */
    private LocalTime endTime;

    /**
     * 地点
     */
    private String address;

    /**
     * 链接
     */
    private String url;

    /**
     * 关联疾病列表
     */
    private String diseaseList;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 最新更新时间
     */
    private LocalDateTime updatedAt;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
