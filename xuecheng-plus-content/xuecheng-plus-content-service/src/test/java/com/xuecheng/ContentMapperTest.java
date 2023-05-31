package com.xuecheng;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.mapper.CourseBaseMapper;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.DatagramSocket;

@SpringBootTest
@Slf4j
public class ContentMapperTest {
    @Autowired
    CourseBaseMapper courseBaseMapper;
    @Test
    public void TestMapper(){
        CourseBase courseBase=courseBaseMapper.selectById(74L);
        log.info("查询到的数据为：{}",JSON.toJSONString(courseBase));
        Assertions.assertNotNull(courseBase);

    }

    @Test
    public void PageRest(){
        // 假设一个接收到的查询擦书
        QueryCourseParamsDto paramsDto = new QueryCourseParamsDto();
//        paramsDto.setAuditStatus("");
        paramsDto.setCourseName("java");
//        paramsDto.setPublishStatus();



        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(paramsDto.getAuditStatus()),CourseBase::getAuditStatus,paramsDto.getAuditStatus());
        queryWrapper.like(StringUtils.isNotEmpty(paramsDto.getCourseName()),CourseBase::getName,paramsDto.getCourseName());
        queryWrapper.eq(StringUtils.isNotEmpty(paramsDto.getPublishStatus()),CourseBase::getStatus,paramsDto.getPublishStatus());


        // 放入分页  当前页码，每页记录数
        Page<CourseBase> page = new Page<>(3,2);
        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page,queryWrapper);
        // 总记录数
        long total = pageResult.getTotal();


        // 返回结果
        PageResult<CourseBase> result = new PageResult<>(pageResult.getRecords(),total,3,2);
        log.info("查询结果：{}",JSON.toJSONString(result));


    }



}
