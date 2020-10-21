package com.umer.alihealth.mapper;

import com.umer.alihealth.entity.CdaActCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * cda2020大会日程表 Mapper 接口
 * </p>
 *
 * @author zephyr
 * @since 2020-10-21
 */
@Repository
@Mapper
public interface CdaActCourseMapper extends BaseMapper<CdaActCourse> {

    CdaActCourse get(@Param("id") Integer id);

}
