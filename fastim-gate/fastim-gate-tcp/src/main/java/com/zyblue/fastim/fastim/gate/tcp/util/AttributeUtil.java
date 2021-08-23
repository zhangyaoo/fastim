package com.zyblue.fastim.fastim.gate.tcp.util;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

/**
 * @author will
 * @date 2021/8/5 23:58
 */
public class AttributeUtil {
    private static final AttributeKey<String> CHANNEL_ATTR_ROUTE_ADDRESS_KEY = AttributeKey.newInstance("address");

    public static void setAddress(Channel channel, String address) {
        channel.attr(CHANNEL_ATTR_ROUTE_ADDRESS_KEY).set(address);
    }

    public static String getAddress(Channel channel) {
        return channel.attr(CHANNEL_ATTR_ROUTE_ADDRESS_KEY).get();
    }
}
