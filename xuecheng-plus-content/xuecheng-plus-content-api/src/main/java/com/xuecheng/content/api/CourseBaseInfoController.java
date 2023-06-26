package com.xuecheng.content.api;

import com.alibaba.fastjson.JSON;
import com.xuecheng.base.exception.ValidationGroups;
import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import com.xuecheng.content.service.CourseBaseInfoService;
import com.xuecheng.content.utils.SecurityUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.core.impl.ReusableLogEventFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
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
  @ApiOperation("课程查询接口")
  @PreAuthorize("hasAuthority('xc_teachmanager_course_list')")
  public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParams){
     log.info("pageParams:{},QueryParams:{}", JSON.toJSONString(pageParams),JSON.toJSONString(queryCourseParams));
      SecurityUtil.XcUser user = SecurityUtil.getUser();
      Long companyId = null;
      if(StringUtils.isEmpty(user.getCompanyId())){
          companyId=Long.parseLong(user.getCompanyId());
      }
      PageResult<CourseBase> pageResult = courseBaseInfoService.queryCourseBaseList(companyId,pageParams,queryCourseParams);
//     log.info("结果集：{}",pageResult);
//     System.out.println(pageResult);
     return pageResult;
  }

    /**
     * 新增课程基本信息
     * @param addCourseDto
     * @return
     */
    @ApiOperation("新增课程基础信息")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated({ValidationGroups.Inster.class}) AddCourseDto addCourseDto){
        //机构id，由于认证系统没有上线暂时硬编码
        Long companyId = 1232141425L;

        return courseBaseInfoService.createCourseBase(companyId,addCourseDto);

    }

    /**
     * 查询课程
     * @param courseId 课程id
     * @return
     */
    @ApiOperation("根据课程id查询课程基础信息")
    @GetMapping("/course/{courseId}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long courseId){
//        // 获取当前用户身份信息
//        Object principal = SecurityContextHolder.getContext() //拿到contenxt
//                .getAuthentication() //  拿到认证信息
//                .getPrincipal();// 拿到身份
//        System.out.println(principal); // 看看打印出来的身份信息时什么样的
//        SecurityUtil.XcUser user = SecurityUtil.getUser();
//        System.out.println(JSON.toJSONString(user));
        return courseBaseInfoService.getCourseBaseInfo(courseId);
    }


    @ApiOperation("修改课程基础信息")
    @PutMapping("/course")
    public CourseBaseInfoDto modifyCourseBase(@RequestBody @Validated EditCourseDto editCourseDto){
        //机构id，由于认证系统没有上线暂时硬编码
        Long companyId = 1232141425L;
        return courseBaseInfoService.updateCourseBase(companyId,editCourseDto);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("/course/{courseId}")
    public void deleteCourse(@PathVariable Long courseId) {
        Long companyId = 1232141425L;
        courseBaseInfoService.delectCourse(companyId,courseId);
    }



}
