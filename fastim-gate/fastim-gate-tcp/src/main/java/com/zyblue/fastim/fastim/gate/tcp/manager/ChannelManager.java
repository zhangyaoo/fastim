package com.zyblue.fastim.fastim.gate.tcp.manager;

import com.zyblue.fastim.common.codec.FastImProtocol;
import com.zyblue.fastim.common.util.ProtoStuffUtils;
import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author will
 * @date 2021/4/30 12:49
 */
@Component
public class ChannelManager {

    private final ConcurrentMap<Long, NioSocketChannel> channelsA = new ConcurrentHashMap<>(1 << 4);
    private final ConcurrentMap<Long, NioSocketChannel> channelsB = new ConcurrentHashMap<>(1 << 4);

    private ConcurrentMap<Long, NioSocketChannel> getChannels(Long userId){
        return (userId & 1) == 1 ? channelsA : channelsB;
    }

    /**
     * 增加连接
     */
    public void add(Long userId, NioSocketChannel channel) {
        getChannels(userId).put(userId, channel);
    }

    /**
     * 获取连接
     */
    public NioSocketChannel get(Long userId) {
        return getChannels(userId).get(userId);
    }

    /**
     * 删除连接
     */
    public void remove(Long userId) {
        getChannels(userId).remove(userId);
    }

    /**
     * 向指定用户发送消息
     */
    public <T> void send(Long userId, FastImProtocol protocol, T msg) {
        Channel channel = getChannels(userId).get(userId);
        if (channel == null) {
            return;
        }
        if (!channel.isActive()) {
            return;
        }
        protocol.setData(ProtoStuffUtils.serialize(msg));
        channel.writeAndFlush(protocol);
    }

    /**
     * 向所有用户发送消息
     */
    public <T> void sendAll(FastImProtocol protocol, T msg) {
        protocol.setData(ProtoStuffUtils.serialize(msg));
        for (Channel channel : channelsA.values()) {
            if (!channel.isActive()) {
                return;
            }
            channel.writeAndFlush(protocol);
        }
        for (Channel channel : channelsB.values()) {
            if (!channel.isActive()) {
                return;
            }
            channel.writeAndFlush(protocol);
        }
    }
}
