package com.atguigu.springcloud.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {
    //模拟简单业务
    public String paymentInfo_OK(Integer id) {
        return "线程池:  " + Thread.currentThread().getName() + "  paymentInfo_OK,id:  " + id + "\t" + "   O(∩_∩)O哈哈~";
    }

    //模拟复杂业务，耗时5秒
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandle", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
    })//此方法允许超时时长为3秒，超过3秒或出现错误调用服务降级方法paymentInfo_TimeOutHandle。
    public String paymentInfo_TimeOut(Integer id) {
        int time=8000;
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //int i=5/0;
        return "线程池:  " + Thread.currentThread().getName() + "   paymentInfo_TimeOut,  " + "  id:  " + id + "\t" + "  O(∩_∩)O哈哈~ " + "  耗时(秒):"+time/1000;
    }

    //当前服务不可用了，做服务降级，兜底的方案都是paymentInfo_TimeOutHandler
    public String paymentInfo_TimeOutHandle(Integer id) {
        return "线程池：  " + Thread.currentThread().getName() + "   系统繁忙，请稍后再试paymentInfo_TimeOutHandle,  " + "  id:  " + id + "\t" + "  o(╥﹏╥)o ";
    }

    //=====服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),// 是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),// 请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"), // 时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),// 失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id) {
        if(id < 0) {
            throw new RuntimeException("******id 不能负数");
        }
        String serialNumber = UUID.randomUUID().toString();

        return Thread.currentThread().getName()+"\t"+"调用成功，流水号: " + serialNumber;
    }
    public String paymentCircuitBreaker_fallback(@PathVariable("id") Integer id) {
        return "id 不能负数，请稍后再试，/(ㄒoㄒ)/~~   id: " +id;
    }

}
