package com.xuecheng.content.service;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;

public interface CourseBaseInfoService {

    // 课程分页查询
    /**
     * 参数查询条件
     * 查询分页参数
     */
    public PageResult<CourseBase> queryCourseBaseList (PageParams pageParams,QueryCourseParamsDto queryCourseParamsDto);


}
