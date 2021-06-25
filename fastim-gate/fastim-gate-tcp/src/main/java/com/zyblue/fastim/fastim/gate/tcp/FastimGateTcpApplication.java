package com.zyblue.fastim.fastim.gate.tcp;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class FastimGateTcpApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastimGateTcpApplication.class, args);
    }

}
