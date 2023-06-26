package com.xuecheng.auth.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.auth.service.AuthService;
import com.xuecheng.ucenter.mapper.XcMenuMapper;
import com.xuecheng.ucenter.mapper.XcUserMapper;
import com.xuecheng.ucenter.model.dto.AuthParamsDto;
import com.xuecheng.ucenter.model.dto.XcUserExt;
import com.xuecheng.ucenter.model.po.XcMenu;
import com.xuecheng.ucenter.model.po.XcUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    XcUserMapper xcUserMapper;

    @Autowired
    ApplicationContext applicationContext;


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        // 将传入的json字符串转成dto对象
        AuthParamsDto authParamsDto =null;
        try{
            authParamsDto = JSON.parseObject(s, AuthParamsDto.class);
        }catch (Exception exception){
            throw new RuntimeException("请求认证的参数异常");
        }
        // 获取认证方式
        // 获取认证类型，beanName就是 认证类型 + 后缀，例如 password + _authservice = password_authservice password_authservice
        String authType = authParamsDto.getAuthType();
        // 根据认证类型，从Spring容器中取出对应的bean
        AuthService authService = applicationContext.getBean(authType + "_authservice", AuthService.class);
        XcUserExt user = authService.execute(authParamsDto);

        // 将返回的 XcUserExt 封装成userdetails
        return  getUserPrincipal(user);
    }

    @Autowired
    XcMenuMapper xcMenuMapper;
    public UserDetails getUserPrincipal(XcUserExt user) {
        // 查询用户权限
        String[] authorities = {"test"};
        List<XcMenu> xcMenus = xcMenuMapper.selectPermissionByUserId(user.getId());
        if(xcMenus.size()>0){
            List<String> permissions = new ArrayList<>();
            xcMenus.forEach(item->{
                permissions.add(item.getCode());
            });
            authorities = permissions.toArray(new String[0]);
        }

        String password = user.getPassword();
        user.setPassword(null);
        String userJsonStr = JSON.toJSONString(user);
        UserDetails userDetails = User.withUsername(userJsonStr).password(password).authorities(authorities).build();
        return userDetails;
    }
}
