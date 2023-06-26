package com.xuecheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages={"com.xuecheng.content.feignclient"})
public class XC_ContentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(XC_ContentServiceApplication.class,args);

    }
}
