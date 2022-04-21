package com.zyblue.fastim.logic.msg.client.holder;


import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author will
 * @date 2022/4/13 19:35
 */
public class ChannelHolder {

    public static final Map<String, Channel> GATE_CHANNEL = new ConcurrentHashMap<>(8);

}
