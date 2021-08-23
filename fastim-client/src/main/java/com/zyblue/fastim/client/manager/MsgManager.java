package com.zyblue.fastim.client.manager;

import io.netty.util.Timeout;
import io.netty.util.collection.IntObjectHashMap;

/**
 * @author will
 * @date 2021/7/12 14:41
 */
public class MsgManager {
    /**
     * TODO thread not safe
     */
    public static final IntObjectHashMap<Timeout> ACK_MSG_LIST = new IntObjectHashMap<>();
}
