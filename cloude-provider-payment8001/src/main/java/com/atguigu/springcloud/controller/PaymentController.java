package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
//Bus动态刷新全局广播：必须先具备良好的RabbitMQ环境先。http://localhost:15672输入账号密码并登录：guest guest
@RestController
@RefreshScope//客户端动态刷新，远程仓库修改了，客户端会自动刷新，无需重启。
//curl -X POST "http://localhost:3344/actuator/bus-refresh"。—次发送，所有的客户端都生效。
//我们这里以刷新运行在8001端口上的cloud-payment-service为例，只通知8001，不通知8002
//  curl -X POST "http://localhost:3344/actuator/bus-refresh/cloud-payment-service:8001"
public class PaymentController {
    @Value("${server.port}")
    public String serverPort;
    @Value("${config.info}")
    private String configInfo;
    @Resource
    private PaymentService paymentService;

    @GetMapping("/payment/get")
    public String getServerPort() {
        return serverPort + "哈哈";
    }

    @GetMapping("/payment/get1")
    public String paymentInfo_OK() {
        return paymentService.paymentInfo_OK(1);
    }

    @GetMapping("/payment/get2")
    public String paymentInfo_TimeOut() {
        return paymentService.paymentInfo_TimeOut(10);
    }

    @GetMapping("/payment/get3/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        return paymentService.paymentCircuitBreaker(id);
    }
    @GetMapping("/payment/configInfo")
    public String configInfo() {
        return configInfo+serverPort;
    }
}
