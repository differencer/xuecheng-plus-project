package com.xuecheng.checkcode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.checkcode.model.FindPswDto;
import com.xuecheng.checkcode.service.CheckCodeService;
import com.xuecheng.checkcode.service.VerifyService;
import com.xuecheng.ucenter.mapper.XcUserMapper;
import com.xuecheng.ucenter.model.po.XcUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.xuecheng.base.utils.PasswordUtil.verify;

@Service
public class VerifyServiceImpl implements VerifyService {
    
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    XcUserMapper userMapper;

    @Autowired
    CheckCodeService picCheckCodeServiceImpl;



    @Override
    public void findPassword(FindPswDto findPswDto) {
        String email = findPswDto.getEmail();
        String checkcode = findPswDto.getCheckcode();
        Boolean verify = picCheckCodeServiceImpl.verify(email, checkcode);
        if (!verify) {
            throw new RuntimeException("验证码输入错误");
        }
        String password = findPswDto.getPassword();
        String confirmpwd = findPswDto.getConfirmpwd();
        if (!password.equals(confirmpwd)) {
            throw new RuntimeException("两次输入的密码不一致");
        }
        LambdaQueryWrapper<XcUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(XcUser::getEmail, findPswDto.getEmail());
        XcUser user = userMapper.selectOne(lambdaQueryWrapper);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        userMapper.updateById(user);
    }
}