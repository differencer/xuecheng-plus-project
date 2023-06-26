package com.xuecheng.content.feignclient;

import com.xuecheng.base.exception.XuechengPlusException;
import org.springframework.web.multipart.MultipartFile;

public class MediaServiceClientFallback implements MediaServiceClient{


    @Override
    public String upload(MultipartFile filedata, String folder, String objectName) {
        throw new XuechengPlusException("调用失败");
        // return null;
    }
}
