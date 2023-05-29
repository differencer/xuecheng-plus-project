package com.xuecheng.content.api;

import com.xuecheng.base.model.PageParams;
import com.xuecheng.base.model.PageResult;
import com.xuecheng.content.model.dto.QueryCourseParamsDto;
import com.xuecheng.content.model.po.CourseBase;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @description 课程信息编辑接口
 * @author Mr.M
 * @date 2022/9/6 11:29
 * @version 1.0
 */
@RestController
public class CourseBaseInfoController {

    /**
     * 测试：http://localhost:63040/content/course/list?pageNo=1&pageSize=10
     * @param
     * @return
     *
     */
 @RequestMapping("/course/list")
  public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCourseParamsDto queryCourseParams){

     System.out.println("yeah");
     return new PageResult<>(new ArrayList<>(),2,3,4);

  }

    @RequestMapping(value = "/fetch/{id}", method = RequestMethod.GET)
    String getDynamicUriValue(@PathVariable String id) {
        System.out.println("ID is " + id);
        return "Dynamic URI parameter fetched";
    }




    }
