package com.xuecheng.content;

import com.alibaba.fastjson.JSON;
import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
public class CourseCategoryTreeTest {
    @Autowired
    CourseCategoryMapper mapper;

    @Autowired
    CourseCategoryService service;
    @Test
    public void MapperTest(){
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = mapper.selectTreeNodes("1");
        System.out.println(JSON.toJSON(courseCategoryTreeDtos));
    }


    @Test
    public void ServiceTest(){
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = service.queryTreeNodes("1");
        System.out.println(JSON.toJSON(courseCategoryTreeDtos));
    }
}
