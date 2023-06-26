package com.xuecheng.checkcode.service;

import com.xuecheng.checkcode.model.FindPswDto;

public interface VerifyService {
    void findPassword(FindPswDto findPswDto);
}