package com.zyblue.fastim.logic.service;

import com.zyblue.fastim.common.pojo.bo.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author will
 * @date 2020/12/23 14:05
 */
public interface TestService {
    void sayHi(LocalDate date1, LocalDateTime date2, Boolean var3, User user);

    User sayHi1(User user);

    void sayHi2(LocalDate date1, LocalDateTime date2, Boolean var3);
}
