package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//自定义全局GlobalFilter(所有请求都会经过此过滤器)：全局日志记录，统一网关鉴权。实现GlobalFilter和Ordered。
//填写监控地址 http://localhost:8001/hystrix.stream 到 http://localhost:9001/hystrix页面的输入框。
@SpringBootApplication
@EnableEurekaClient
public class GateWayMain9527 {
    public static void main(String[] args) {
        SpringApplication.run(GateWayMain9527.class, args);
    }
}
