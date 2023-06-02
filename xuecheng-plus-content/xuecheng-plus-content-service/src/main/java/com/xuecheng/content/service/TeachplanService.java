package com.xuecheng.content.service;

import com.xuecheng.content.model.dto.BindTeachplanMediaDto;
import com.xuecheng.content.model.dto.SaveTeachplanDto;
import com.xuecheng.content.model.dto.TeachplanDto;
import com.xuecheng.content.model.po.TeachplanMedia;

import java.util.List;

public interface TeachplanService {
    List<TeachplanDto> findTeachplanTree(Long courseId);


    /**
     * @description 只在课程计划
     * @param teachplanDto  课程计划信息
     * @return void
     * @author Mr.M
     * @date 2022/9/9 13:39
     */
    public void saveTeachplan(SaveTeachplanDto teachplanDto);

    /**
     * 删除课程计划
     * @param teachplanId
     */

    void deleteTeachplan(Long teachplanId);


    void orderByTeachplan(String moveType, Long teachplanId);



    public TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto);

    void unassociationMedia(Long teachPlanId, String mediaId);
}