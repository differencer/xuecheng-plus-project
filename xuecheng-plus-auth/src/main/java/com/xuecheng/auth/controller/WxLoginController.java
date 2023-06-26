package com.xuecheng.auth.controller;

import com.xuecheng.auth.service.impl.WxAuthServiceImpl;
import com.xuecheng.ucenter.model.po.XcUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Slf4j
@Controller
public class WxLoginController {
    @Autowired
    WxAuthServiceImpl wxAuthService;

    @RequestMapping("/wxLogin")
    public String wxLogin(String code, String state) throws IOException {
        log.debug("微信扫码回调,code:{},state:{}",code,state);
        XcUser xcUser = wxAuthService.wxAuth(code);
        if(xcUser==null){
            return "redirect:http://www.51xuecheng.cn/error.html";
        }
        String username = xcUser.getUsername();
        log.debug("执行完微信登录获取用户信息，下面验证用户信息,username:{},authType:{}",username,"wx");
        return "redirect:http://www.51xuecheng.cn/sign.html?username="+username+"&authType=wx";
    }
}