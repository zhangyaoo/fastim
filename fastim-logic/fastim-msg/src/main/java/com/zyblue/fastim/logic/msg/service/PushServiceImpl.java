package com.zyblue.fastim.logic.msg.service;


import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author will
 * @date 2021/12/27 11:06
 */
@Service
public class PushServiceImpl implements PushService{
    @Override
    public void push(List<Long> userIds, String msg) {

    }

    @Override
    public void pushAll(String msg) {

    }
}
