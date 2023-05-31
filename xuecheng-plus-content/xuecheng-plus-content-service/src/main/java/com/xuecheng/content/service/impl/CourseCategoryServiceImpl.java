package com.xuecheng.content.service.impl;

import com.xuecheng.content.mapper.CourseCategoryMapper;
import com.xuecheng.content.model.dto.CourseCategoryTreeDto;
import com.xuecheng.content.service.CourseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {
    /**
     * 实现 查询传入id的所有节点，以及将其封装成为一个树状结构
     * @param id
     * @return
     */

    @Autowired
    CourseCategoryMapper courseCategoryMapper;
    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        // 未处理的数据
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);



        // 开始封装
        // 将list 转成 Map 方便查询使用 (在这一步排除根节点)
        Map<String,CourseCategoryTreeDto> mapTemp =
                courseCategoryTreeDtos
                        .stream()
                        .filter(item -> !(item.getId().equals(id))) // 过滤根节点
                        .collect(Collectors.toMap(key->key.getId(),value->value,(key1,key2)->key2));
                        //(key1,key2)->key2) 的含义是 ： 当 list集合元素重复时选择哪一个？
        // 返回 的 list
        List<CourseCategoryTreeDto> result = new ArrayList<>();
        // 遍历元素(排除根节点) 将将其放入结果集当中
        courseCategoryTreeDtos
                .stream()
                .filter(item->!(item.getId().equals(id)))
                .forEach(item->{    // 遍历元素
                    // 第一层
                    if(item.getParentid().equals(id)){
                        result.add(item);
                    }
                    // 不是第一层，找到了他的父节点 （一定可以找到，因为时按顺序拍的）
                    // 父节点
                    CourseCategoryTreeDto parentDto = mapTemp.get(item.getParentid());
                    // 在父节点的 子节点类表中添加 子节点
                    if(parentDto!=null){ //其实不用加的，因为已经排除了根节点
                        if(parentDto.getChildrenTreeNodes()==null){
                            parentDto.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                        }
                        parentDto.getChildrenTreeNodes().add(item);
                    }
                });
                return result;

    }
}
