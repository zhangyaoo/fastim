package com.zyblue.fastim.logic;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class FastimLogicApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastimLogicApplication.class, args);
    }

}
