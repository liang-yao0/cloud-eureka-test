###############Eureka服务端集群####################
#以管理员身份打开记事本打开文件C:\Windows\System32\drivers\etc\hosts添加
#127.0.0.1      eureka7001.com
#127.0.0.1	    eureka7002.com
##########Eureka服务端7001，主启动类@EnableEurekaServer
server:
  port: 7001
eureka:
  instance:
    hostname: eureka7001.com                #eureka服务端名称
  client:
    register-with-eureka: false             #不想注册中心注册自己
    fetch-registry: false                   #自己为注册中心，我是维护服务实例，不需要去检索服务
    service-url:                            #与eureka server交互的地址
      defaultZone: http://eureka7002.com:7002/eureka/
      #defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ #单机

##########Eureka服务提供者8001，主启动类@EnableEurekaClient。
server:
  port: 8001
spring:
  application:
    name: cloud-payment-service
eureka:
  client:
    register-with-eureka: true    #将自己注册进服务中心
    #是否从eureka server抓取已有的注册信息，单点无所谓，集群必须为true才能配合ribbon使用负载均衡
    fetch-registry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7001.com:7001/eureka
  instance:
    instance-id: payment8001        #修改注册中心显示，原本为主机名:服务名:端口。现在为payment8002
    prefer-ip-address: true         #访问路径可以显示ip地址，鼠标放在instance-id上可以显示ip
    
##########Eureka服务调用者80，主启动类@EnableEurekaClient 
server:
  port: 80
spring:
  application:
    name: cloud-order-service
eureka:
  client:
    register-with-eureka: true
    fetch-registry: true
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka,http://eureka7001.com:7001/eureka    
      
      
      
      
      
#zipkin调用链路追踪
spring:
  zipkin: 
    base-url: http://localhost:9411     #追踪的地方9411  
  sleuth: 
    sampler:
      probability: 1                    #采样率值介于 0 到 1 之间，1 则表示全部采集      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      
      