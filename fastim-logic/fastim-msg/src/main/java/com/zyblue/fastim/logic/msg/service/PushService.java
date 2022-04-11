package com.zyblue.fastim.logic.msg.service;

import java.util.List;

/**
 * @author will
 * @date 2021/12/27 11:06
 */
public interface PushService {
    /**
     * 推送部分用户
     * @param userIds 用户IDs
     * @param msg msg
     */
    void push(List<Long> userIds, String msg);

    /**
     * 推送所有用户
     * @param msg msg
     */
    void pushAll(String msg);
}
