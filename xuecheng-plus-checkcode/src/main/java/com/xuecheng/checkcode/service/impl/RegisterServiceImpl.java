package com.xuecheng.checkcode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xuecheng.checkcode.model.RegisterDto;
import com.xuecheng.checkcode.service.RegisterService;
import com.xuecheng.ucenter.mapper.XcRoleMapper;
import com.xuecheng.ucenter.mapper.XcUserMapper;
import com.xuecheng.ucenter.mapper.XcUserRoleMapper;
import com.xuecheng.ucenter.model.po.XcUser;
import com.xuecheng.ucenter.model.po.XcUserRole;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    PicCheckCodeServiceImpl picCheckCodeService;

    @Autowired
    XcUserMapper userMapper;
    @Autowired
    XcUserRoleMapper xcUserRoleMapper;

    @Override
    @Transactional
    public void register(RegisterDto registerDto) {
        String uuid = UUID.randomUUID().toString();
        String email = registerDto.getEmail();
        String checkcode = registerDto.getCheckcode();
        Boolean verify = picCheckCodeService.verify(email, checkcode);
        if (!verify) {
            throw new RuntimeException("验证码输入错误");
        }
        String password = registerDto.getPassword();
        String confirmpwd = registerDto.getConfirmpwd();
        if (!password.equals(confirmpwd)) {
            throw new RuntimeException("两次输入的密码不一致");
        }
        LambdaQueryWrapper<XcUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(XcUser::getEmail, registerDto.getEmail());
        XcUser user = userMapper.selectOne(lambdaQueryWrapper);
        if (user != null) {
            throw new RuntimeException("用户已存在，一个邮箱只能注册一个账号");
        }
        XcUser xcUser = new XcUser();
        BeanUtils.copyProperties(registerDto, xcUser);
        xcUser.setPassword(new BCryptPasswordEncoder().encode(password));
        xcUser.setId(uuid);
        xcUser.setUtype("101001");  // 学生类型
        xcUser.setStatus("1");
        xcUser.setName(registerDto.getNickname());
        xcUser.setCreateTime(LocalDateTime.now());
        int insert = userMapper.insert(xcUser);
        if (insert <= 0) {
            throw new RuntimeException("新增用户信息失败");
        }
        XcUserRole xcUserRole = new XcUserRole();
        xcUserRole.setId(uuid);
        xcUserRole.setUserId(uuid);
        xcUserRole.setRoleId("17");
        xcUserRole.setCreateTime(LocalDateTime.now());
        int insert1 = xcUserRoleMapper.insert(xcUserRole);
        if (insert1 <= 0) {
            throw new RuntimeException("新增用户角色信息失败");
        }
    }
}
