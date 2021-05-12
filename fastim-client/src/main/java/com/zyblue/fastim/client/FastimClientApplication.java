package com.zyblue.fastim.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FastimClientApplication{

    private final static Logger logger = LoggerFactory.getLogger(FastimClientApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FastimClientApplication.class, args);
        logger.info("server start");
    }
}
