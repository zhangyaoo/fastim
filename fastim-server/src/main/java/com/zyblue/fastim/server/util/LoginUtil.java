package com.zyblue.fastim.server.util;

import com.zyblue.fastim.common.constant.ChannelAttr;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * 是否登录
 */
public class LoginUtil {
    /*public static void markAsLogin(Channel channel) {
        channel.attr(AttributeKey.newInstance(ChannelAttr.TOKEN.getVal())).set(true);
    }*/

    public static boolean hasLogin(Channel channel) {
        Attribute<String> loginAttr = channel.attr(AttributeKey.newInstance(ChannelAttr.TOKEN.getVal()));

        return loginAttr.get() != null;
    }
}
