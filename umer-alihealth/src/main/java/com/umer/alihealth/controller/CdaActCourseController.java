package com.umer.alihealth.controller;


import com.umer.alihealth.entity.CdaActCourse;
import com.umer.alihealth.service.ICdaActCourseService;
import com.umer.common.api.Result;
import com.umer.common.api.ResultCode;
import com.umer.common.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * <p>
 * cda2020大会日程表 前端控制器
 * </p>
 *
 * @author zephyr
 * @since 2020-10-21
 */
@RestController
@RequestMapping("/course")
public class CdaActCourseController {

    @Autowired
    private ICdaActCourseService cdaActCourseService;

    @GetMapping("/get")
    public Result<CdaActCourse> get(){
        int i = new Random().nextInt();
        if(i%2==0){
            throw new ApiException(ResultCode.FAILED);
        }
        return Result.success(cdaActCourseService.get());
    }
}

