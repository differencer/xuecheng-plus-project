package com.xuecheng.content.api;

import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    @GetMapping("/test")
    public  String test(String s){
        System.out.println("s");
        return "收到字符串："+s;
    }

    @RequestMapping(value = "/id")
        // 实现请求参数 id 与 处理方法参数 personId 的绑定。
    String getIdByValue1(@RequestParam("id") String personId) {
        return "id?="+personId;
    }

    @RequestMapping(value = "/fetch/{id}", method = RequestMethod.GET)
    String getDynamicUriValue(@PathVariable String id) {
        System.out.println("ID is " + id);
        return "Dynamic URI parameter fetched";
    }

}
