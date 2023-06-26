package com.xuecheng.content.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@Slf4j
public class FreemarkerController {

    @GetMapping("/testfreemarker")
    public ModelAndView test(){
        log.info("ceshi:");
        ModelAndView modelAndView = new ModelAndView();
        //设置模型数据
        modelAndView.addObject("name","小明");
        //设置模板名称
        modelAndView.setViewName("test"); // 刚才的模板名称
        return modelAndView;
    }
}