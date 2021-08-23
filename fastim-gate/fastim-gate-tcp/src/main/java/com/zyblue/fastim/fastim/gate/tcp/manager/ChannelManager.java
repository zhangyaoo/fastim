package com.zyblue.fastim.fastim.gate.tcp.manager;

import com.zyblue.fastim.common.codec.FastImMsg;
import com.zyblue.fastim.common.util.ProtoStuffUtils;
import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author will
 * @date 2021/4/30 12:49
 */
@Component
public class ChannelManager {

    private final static Logger log = LoggerFactory.getLogger(ChannelManager.class);


    /**
     * TODO LRU算法
     */
    private final ConcurrentMap<Long, NioSocketChannel> channels = new ConcurrentHashMap<>(1 << 4);

    /**
     * 增加连接
     */
    public void add(Long userId, NioSocketChannel channel) {
        channels.put(userId, channel);
    }

    /**
     * 获取连接
     */
    public NioSocketChannel get(Long userId) {
        return channels.get(userId);
    }

    /**
     * 删除连接
     */
    public void remove(Long userId) {
        channels.remove(userId);
    }

    /**
     * 向指定用户发送消息
     */
    public <T> void send(Long userId, FastImMsg protocol, T msg) {
        Channel channel = channels.get(userId);
        if (channel == null) {
            return;
        }
        if (!channel.isActive()) {
            return;
        }
        protocol.setData(ProtoStuffUtils.serialize(msg));

        /*
         * 当netty的缓冲区满的时候,会将channel设置为unWritable状态。这个时候需要合理设置高低水位线
         */
        if (channel.isActive() && channel.isWritable()) {
            channel.writeAndFlush(protocol);
        } else {
            // TODO 待会再发送
            // TODO 多次下推之后仍然没有成功的话，就移除channel连接并且关闭，并且清除ack队列的消息
            log.error("message dropped");
        }
    }

    /**
     * 向所有用户发送消息
     */
    public <T> void sendAll(FastImMsg protocol, T msg) {
        protocol.setData(ProtoStuffUtils.serialize(msg));
        for (Channel channel : channels.values()) {
            if (!channel.isActive()) {
                return;
            }
            channel.writeAndFlush(protocol);
        }
        for (Channel channel : channels.values()) {
            if (channel.isActive() && channel.isWritable()) {
                channel.writeAndFlush(protocol);
            } else {
                log.error("message dropped");
            }
        }
    }
}
