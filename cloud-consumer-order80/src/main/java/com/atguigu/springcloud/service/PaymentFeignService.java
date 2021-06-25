package com.atguigu.springcloud.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("CLOUD-PAYMENT-SERVICE")
public interface PaymentFeignService {
    @GetMapping("/payment/get")
    public String getServerPort();

    @GetMapping("/payment/get1")
    public String paymentInfo_OK();

    @GetMapping("/payment/get2")
    public String paymentInfo_TimeOut();

    @GetMapping("/payment/get3/{id}")
    public String paymentCircuitBreaker(@PathVariable("id") Integer id);

}
