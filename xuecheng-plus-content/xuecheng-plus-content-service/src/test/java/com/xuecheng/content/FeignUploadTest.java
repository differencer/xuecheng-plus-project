package com.xuecheng.content;

import com.xuecheng.content.config.MultipartSupportConfig;
import com.xuecheng.content.feignclient.MediaServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@SpringBootTest
public class FeignUploadTest {

    @Autowired
    MediaServiceClient mediaServiceClient;

    //远程调用，上传文件
    @Test
    public void test() {
    
        MultipartFile multipartFile = MultipartSupportConfig
                .getMultipartFile(new File("E:\\test\\freemarker\\test124.html"));

        mediaServiceClient.upload(
                        multipartFile,
                        "course",
                    "course/test124.html");
    }

}
