package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
/*
配置读取规则
master分支上读取：http://config-3344.com:3344/master/config-prod.yml
dev分支上读取：http://config-3344.com:3344/dev/config-dev.yml
yml配置的分支上读取(不推荐)：http://config-3344.com:3344/config-dev.yml
* */
@SpringBootApplication
@EnableConfigServer//允许配置中心提供服务
public class ConfigCenterMain3344 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenterMain3344.class, args);
    }
}
