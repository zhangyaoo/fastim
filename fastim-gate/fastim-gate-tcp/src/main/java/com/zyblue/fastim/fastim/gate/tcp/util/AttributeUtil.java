package com.zyblue.fastim.fastim.gate.tcp.util;

import com.zyblue.fastim.common.pojo.ServerInfo;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.List;

/**
 * @author will
 * @date 2021/8/5 23:58
 */
public class AttributeUtil {
    private static final AttributeKey<List<ServerInfo>> CHANNEL_ATTR_ROUTE_ADDRESS_KEY = AttributeKey.newInstance("address");

    public static void setAddress(Channel channel, List<ServerInfo> addresss) {
        channel.attr(CHANNEL_ATTR_ROUTE_ADDRESS_KEY).set(addresss);
    }

    public static List<ServerInfo> getAddress(Channel channel) {
        return channel.attr(CHANNEL_ATTR_ROUTE_ADDRESS_KEY).get();
    }
}
