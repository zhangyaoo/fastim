package com.zyblue.fastim.leaf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.system.ApplicationPidFileWriter;

@SpringBootApplication
public class FastimLeafApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastimLeafApplication.class, args);
    }

}
