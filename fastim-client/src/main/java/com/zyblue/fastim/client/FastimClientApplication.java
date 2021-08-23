package com.zyblue.fastim.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FastimClientApplication{
    public static void main(String[] args) {
        // windows根据pid查看端口 netstat -ano | findstr port
        SpringApplication.run(FastimClientApplication.class, args);
    }
}
