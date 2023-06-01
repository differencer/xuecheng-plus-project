package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.AddCourseDto;
import com.xuecheng.content.model.dto.CourseBaseInfoDto;
import com.xuecheng.content.model.dto.EditCourseDto;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

public interface CourseBaseInfoService {

    // 课程分页查询
    /**
     * 参数查询条件
     * 查询分页参数
     */
    public PageResult<CourseBase> queryCourseBaseList (PageParams pageParams,QueryCourseParamsDto queryCourseParamsDto);


    /**
     * @description 添加课程基本信息
     * @param companyId  教学机构id
     * @param addCourseDto  课程基本信息
     * @return com.xuecheng.content.model.dto.CourseBaseInfoDto
     * @author
     * @date 2022/9/7 17:51
     */
    CourseBaseInfoDto createCourseBase(Long companyId, AddCourseDto addCourseDto);

    /**
     * 查询课程
     * @param courseId
     * @return
     */
    public CourseBaseInfoDto getCourseBaseInfo(long courseId);

    /** 修改课程
     *
     * @param companyId
     * @param dto
     * @return
     */
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto);

    void delectCourse(Long companyId, Long courseId);
}
