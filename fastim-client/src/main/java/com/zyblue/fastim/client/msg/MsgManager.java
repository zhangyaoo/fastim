package com.zyblue.fastim.client.msg;

import io.netty.util.Timeout;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author will
 * @date 2021/7/12 14:41
 */
public class MsgManager {
    /**
     * ACK重试队列
     */
    public static final ConcurrentHashMap<Integer, Timeout> ACK_MSG_TIMEOUT_LIST = new ConcurrentHashMap<>();

    /**
     * 消息请求映射
     */
    //public static final ConcurrentHashMap<Integer, MsgRequest> MSG_REQUEST_LIST = new ConcurrentHashMap<>();

    /**
     * 会话ID和最新的会话消息ID映射
     */
    public static final ConcurrentHashMap<Long, Long> SESSION_MSG_ID_LIST = new ConcurrentHashMap<>();
}
