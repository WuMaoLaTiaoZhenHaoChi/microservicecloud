package com.xiaobawang.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@EnableDiscoveryClient //服务发现
@SpringBootApplication
public class Microservicecloud_8001_APP {

    public static void main(String[] args) {
        SpringApplication.run(Microservicecloud_8001_APP.class,args);
    }

}
