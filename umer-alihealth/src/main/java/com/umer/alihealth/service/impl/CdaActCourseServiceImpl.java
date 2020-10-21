package com.umer.alihealth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.umer.alihealth.entity.CdaActCourse;
import com.umer.alihealth.mapper.CdaActCourseMapper;
import com.umer.alihealth.service.ICdaActCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * cda2020大会日程表 服务实现类
 * </p>
 *
 * @author zephyr
 * @since 2020-10-21
 */
@Service
public class CdaActCourseServiceImpl extends ServiceImpl<CdaActCourseMapper, CdaActCourse> implements ICdaActCourseService {

    @Override
    public CdaActCourse get() {
        return this.getOne(new LambdaQueryWrapper<CdaActCourse>().eq(CdaActCourse::getId, 1));
    }
}
