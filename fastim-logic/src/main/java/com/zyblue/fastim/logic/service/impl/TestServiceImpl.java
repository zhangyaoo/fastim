package com.zyblue.fastim.logic.service.impl;

import com.zyblue.fastim.common.pojo.bo.User;
import com.zyblue.fastim.logic.service.TestService;
import org.apache.dubbo.config.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author will
 * @date 2020/12/23 14:06
 */
@Service
public class TestServiceImpl implements TestService {

    private final static Logger logger = LoggerFactory.getLogger(TestServiceImpl.class);

    @Override
    public void sayHi(LocalDate var1, LocalDateTime var2, Boolean var3, User var4) {
        logger.info("var1:{}, var2:{}, var3:{}, var4:{}", var1, var2, var3, var4);
    }

    @Override
    public User sayHi1(User user) {
        logger.info("user:{}", user);
        return user;
    }

    @Override
    public void sayHi2(LocalDate var1, LocalDateTime var2, Boolean var3) {
        logger.info("var1:{}, var2:{}, var3:{}", var1, var2, var3);
    }
}
