package com.zyblue.fastim.client.manager;

import com.zyblue.fastim.common.pojo.message.MsgRequest;
import io.netty.util.Timeout;
import io.netty.util.collection.IntObjectHashMap;

/**
 * @author will
 * @date 2021/7/12 14:41
 */
public class MsgManager {
    /**
     * ACK重试队列
     * TODO thread not safe
     */
    public static final IntObjectHashMap<Timeout> ACK_MSG_TIMEOUT_LIST = new IntObjectHashMap<>();

    /**
     * 消息请求映射
     */
    public static final IntObjectHashMap<MsgRequest> MSG_REQUEST_LIST = new IntObjectHashMap<>();
}
