package com.xuecheng;


import com.xuecheng.ucenter.mapper.XcUserMapper;
import com.xuecheng.ucenter.model.po.XcUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserTest {

    @Autowired
    XcUserMapper xcUserMapper;
    @Test
    public void test(){
        XcUser xcUser = xcUserMapper.selectById(50);
        System.out.println(xcUser.getName());
    }
}
