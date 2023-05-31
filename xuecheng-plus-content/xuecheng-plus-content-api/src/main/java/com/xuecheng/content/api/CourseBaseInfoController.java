package com.xuecheng.content.api;

import com.alibaba.fastjson.JSON;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;

/**
 * @description 课程信息编辑接口
 * @author Mr.M
 * @date 2022/9/6 11:29
 * @version 1.0
 */
@RestController
@Slf4j
public class CourseBaseInfoController {



    @Autowired
    CourseBaseInfoService courseBaseInfoService;

    /**
     * 测试：http://localhost:63040/content/course/list?pageNo=1&pageSize=10
     * @param
     * @return
     *
     */
  @PostMapping("/course/list")
  public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParams){
     log.info("pageParams:{},QueryParams:{}", JSON.toJSONString(pageParams),JSON.toJSONString(queryCourseParams));

     PageResult<CourseBase> pageResult = courseBaseInfoService.queryCourseBaseList(pageParams,queryCourseParams);
//     log.info("结果集：{}",pageResult);
//     System.out.println(pageResult);
     return pageResult;
  }




}
