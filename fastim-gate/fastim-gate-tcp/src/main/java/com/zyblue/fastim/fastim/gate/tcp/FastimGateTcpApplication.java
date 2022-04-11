package com.zyblue.fastim.fastim.gate.tcp;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.client.naming.utils.NetUtils;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class FastimGateTcpApplication implements CommandLineRunner {
    @NacosInjected
    private NamingService namingService;

    @Value("${instance.name}")
    private String instanceName;

    @Value("${fastim.server.port}")
    private int nettyPort;

    public static void main(String[] args) {
        /*
         * what,原理：ByteBuf是引用计数来做内存回收判断的，当new一个ByteBuf时候，引用计数加1，并且会增加一个弱引用的ByteBuf包装类，它会加入到allLeak的集合中，ByteBuf引用计数为0时候，allLeak会移除它。当ByteBuf被回收的时候，包装类的弱引用会加入到referenceQueue队列中。通过判断referenceQueue队列中的的对象的是否在allLeak中，来判断是否泄露.具体的实现类 ResourceLeakDetector netty自带的内存泄露的类.
         * how,如何检测：泄露检测开关：io.netty.leakDetection.level 内存泄漏级别  1、SIMPLE 默认级别，抽样测试内存泄露 2、PARANOID 最高级别，对所有的对象进行内存蟹柳检测，适合在上线前进行测试
         */
        System.setProperty("io.netty.leakDetection.level", "PARANOID");

        SpringApplication.run(FastimGateTcpApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        // 通过Naming服务注册实例到注册中心
        namingService.registerInstance(instanceName, NetUtils.localIP(), nettyPort);
    }
}
