package com.zyblue.fastim.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FastimServerApplication implements CommandLineRunner {

    private final static Logger logger = LoggerFactory.getLogger(FastimServerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FastimServerApplication.class, args);
        logger.info("server start");
    }

    @Override
    public void run(String... args) throws Exception {
        //Thread thread = new Thread();
    }
}
