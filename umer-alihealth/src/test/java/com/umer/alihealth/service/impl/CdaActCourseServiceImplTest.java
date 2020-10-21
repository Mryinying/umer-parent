package com.umer.alihealth.service.impl;


import com.umer.alihealth.entity.CdaActCourse;
import com.umer.alihealth.service.ICdaActCourseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CdaActCourseServiceImplTest {

    @Autowired
    private ICdaActCourseService cdaActCourseService;

    @Test
    public void get(){
        CdaActCourse cdaActCourse = cdaActCourseService.get();
        System.out.println(cdaActCourse);
    }

}