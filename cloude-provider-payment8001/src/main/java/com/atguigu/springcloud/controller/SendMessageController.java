package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.service.IMessageProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendMessageController {
    @Resource
    private IMessageProvider messageProvider;
    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/payment/sendMessage")
    public String sendMessage() {
        return messageProvider.send()+"==="+serverPort;
    }

}

