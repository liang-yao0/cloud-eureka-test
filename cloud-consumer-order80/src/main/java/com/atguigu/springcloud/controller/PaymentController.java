package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.PaymentFeignService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PaymentController {
    @Resource
    private PaymentFeignService paymentFeignService;

    @GetMapping("/consumer/payment/get")
    public String getServerPort() {
        return paymentFeignService.getServerPort();
    }

    @GetMapping("/consumer/payment/get1")
    public String paymentInfo_OK() {
        return paymentFeignService.paymentInfo_OK();
    }

    @HystrixCommand(fallbackMethod = "paymentTimeOutFallBackMethod",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000")
    })//80端口只等待3秒，否则就调用fallback
    @GetMapping("/consumer/payment/get2")
    public String paymentInfo_TimeOut() {
        return paymentFeignService.paymentInfo_TimeOut();
    }


    @GetMapping("/consumer/payment/get3/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        return paymentFeignService.paymentCircuitBreaker(id);
    }

    public String paymentTimeOutFallBackMethod(){
        return "我是消费者80，对方支付系统繁忙请10秒后再试，活自己运行出错请检查自己，o(╥﹏╥)o";
    }
}
