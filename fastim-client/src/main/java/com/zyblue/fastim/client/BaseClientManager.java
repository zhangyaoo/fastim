package com.zyblue.fastim.client;

import com.zyblue.fastim.client.event.EventBusCenter;
import com.zyblue.fastim.client.msg.MsgManager;
import com.zyblue.fastim.client.msg.MsgSubscribeManager;
import com.zyblue.fastim.client.service.SequenceIdService;
import com.zyblue.fastim.client.task.MsgAckTimerTask;
import com.zyblue.fastim.common.codec.FastImMsg;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author will
 * @date 2021/12/2 15:46
 */
public abstract class BaseClientManager {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    protected int sendDuration;
    protected int sendRetry;
    protected NioSocketChannel channel;
    protected int connectRetry;
    protected String host;
    protected int port;
    protected NioEventLoopGroup ackGroup = new NioEventLoopGroup(1);

    /**
     * 发消息ack 时间轮
     */
    protected final HashedWheelTimer ackTimer = new HashedWheelTimer(new DefaultThreadFactory("send-msg-ack-timer-websocket"), 100,
            TimeUnit.MILLISECONDS, 1024, false);

    /**
     * 发送消息
     *
     * @param msg 消息体
     */
    public void send(FastImMsg msg) {
        if (channel == null) {
            logger.error("[channel][未初始化]");
            reconnect();
        }
        if (!channel.isActive()) {
            logger.error("[send][连接未激活]");
            channel.close().addListener(future -> {
                if (future.isSuccess()) {
                    reconnect();
                }
            });
        }else {
            // 发送消息
            int sequenceId = SequenceIdService.getInstance().generateSequenceId();
            msg.setSequenceId(sequenceId);
            if (!channel.isWritable()) {
                logger.info("channel outbound buffer full, can not writable");
                try {
                    channel.writeAndFlush(msg).sync().addListener(future -> {
                        if(future.isSuccess()){
                            operationSuccess(sequenceId, msg);
                        }
                    });
                } catch (InterruptedException e) {
                    logger.error("error", e);
                }
            }else {
                channel.writeAndFlush(msg).addListener(future -> {
                    if (future.isSuccess()) {
                        operationSuccess(sequenceId, msg);
                    }
                });
            }
        }
    }

    private void operationSuccess(int sequenceId, FastImMsg msg) {
        Timeout timeout = ackTimer.newTimeout(new MsgAckTimerTask(channel, sendDuration, sendRetry, msg), sendDuration, TimeUnit.MILLISECONDS);
        MsgManager.ACK_MSG_TIMEOUT_LIST.put(sequenceId, timeout);
    }

    public void connectFuture(Future<?> future){
        if (future.isSuccess()) {
            logger.info("连接成功!");
            EventBusCenter.register(new MsgSubscribeManager());
        } else if (connectRetry == 0) {
            logger.info("重试次数已用完，放弃连接！");
        } else {
            /*
             * 当网络异常恢复后，大量客户端可能会同时发起TCP重连及进行应用层请求，可能会造成服务端过载、网络带宽耗尽等问题，所以增加随机退让机制
             */
            int delay = 1 + ThreadLocalRandom.current().nextInt(0, 5);
            ackGroup.schedule(() -> {
                connect(host, port);
                connectRetry--;
            }, delay, TimeUnit.SECONDS);
        }
    }

    public void disconnect(){
        ackTimer.stop();
    }

    /**
     * connect
     * @param host host
     * @param port port
     * @return res
     */
    public abstract boolean connect(String host, Integer port);

    /**
     * reconnect
     */
    public abstract void reconnect();
}
