package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
/*
全局服务降级
	* 定义全局fallback方法，@DefaultProperties(defaultFallback = "payment_Global_FallbackMethod")，在类上指定，属性用法与@HystrixCommand相同。
	* 方法上指定了具体的@HystrixCommand，就用具体的。只有@HystrixCommand没有具体，就用全局的。

问题：给每个方法都要写一个fallback方法，所以fallback和controller方法混合。
解决：编写fallback的类继承feignService接口，实现服务方法，内部编写具体的fallback代码。
      在feignService接口上的@FeignClient(fallback = PaymentFallbackService.class)//指定处理fallback方法的类。
* */
/*
Ribbon默认自带的负载规则，lRule：根据特定算法中从服务列表中选取一个要访问的服务
* RoundRobinRule 		    轮询，默认
* RandomRule 			    随机
* RetryRule 			    先按照RoundRobinRule的策略获取服务，如果获取服务失败则在指定时间内会进行重试，获取可用的服务
* WeightedResponseTimeRule 	对RoundRobinRule的扩展，响应速度越快的实例选择权重越大，越容易被选择
* BestAvailableRule 		会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务
* AvailabilityFilteringRule 先过滤掉故障实例，再选择并发较小的实例
* ZoneAvoidanceRule 		默认规则,复合判断server所在区域的性能和server的可用性选择服务器
2.Ribbon负载规则替换：新建MySelfRule规则类，@Bean注入IRule，返回那种规则对象，负载就为那种规则。******不要将Ribbon配置类与主启动类同包。
主启动类：@RibbonClient(name = "CLOUD_PAYMENT_SERVICE", configuration = MySelfRule.class)	CLOUD_PAYMENT_SERVICE服务的负载规则使用自定的。
* */
@SpringBootApplication
@EnableFeignClients
@EnableHystrix
public class PaymentOrderMain80 {
    public static void main(String[] args) {
        SpringApplication.run(PaymentOrderMain80.class,args);
    }
}
