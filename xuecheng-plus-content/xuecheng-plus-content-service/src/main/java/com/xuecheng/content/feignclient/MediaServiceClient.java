package com.xuecheng.content.feignclient;


import com.xuecheng.content.config.MultipartSupportConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "media-api",configuration = MultipartSupportConfig.class,fallbackFactory = MediaServiceClientFallbackFactory.class)
public interface MediaServiceClient {

    @RequestMapping(value = "media/upload/coursefile",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String upload(@RequestPart("filedata") MultipartFile filedata,
                                      @RequestParam(value = "folder",required=false) String folder,
                                      @RequestParam(value = "objectName",required=false) String objectName);


}