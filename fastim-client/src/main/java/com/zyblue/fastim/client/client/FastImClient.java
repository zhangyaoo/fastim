package com.zyblue.fastim.client.client;

import com.zyblue.fastim.client.handler.FastImClientHandler;
import com.zyblue.fastim.client.handler.MyFastImDecoder;
import com.zyblue.fastim.client.handler.MyFastImEncoder;
import com.zyblue.fastim.client.manager.MsgManager;
import com.zyblue.fastim.client.service.SequenceIdService;
import com.zyblue.fastim.client.task.MsgAckTimerTask;
import com.zyblue.fastim.common.codec.FastImMsg;
import com.zyblue.fastim.common.pojo.message.MsgRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * 滚滚长江东流水ing
 */
public class FastImClient {

    private static final FastImClient CLIENT = new FastImClient();

    public static FastImClient getInstance() {
        return CLIENT;
    }

    private final static Logger logger = LoggerFactory.getLogger(FastImClient.class);

    private NioEventLoopGroup workerGroup;

    public FastImClient() {
        if (bootstrap == null) {
            bootstrap = initBootstrap();
        }
    }

    /**
     * 客户端channel
     */
    private NioSocketChannel channel;

    private Bootstrap bootstrap;

    /**
     * 发消息ack 时间轮
     */
    private final HashedWheelTimer ackTimer = new HashedWheelTimer(new DefaultThreadFactory("send-msg-ack-timer"), 100,
            TimeUnit.MILLISECONDS, 1024, false);


    public Bootstrap initBootstrap() {
        logger.info("fastim client starting..");

        workerGroup = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        channel.pipeline()
                                /*
                                 * 客户端发现 180 秒未从服务端读取到消息，主动断开连接
                                 */
                                .addLast(new ReadTimeoutHandler(180, TimeUnit.SECONDS))
                                /*
                                 * 服务端每 60 秒向服务端发起一次心跳消息，保证客户端端可以读取到消息。有3次机会保证不断开
                                 */
                                .addLast(new IdleStateHandler(60, 0, 0))
                                .addLast(new MyFastImDecoder())
                                .addLast(new MyFastImEncoder())
                                .addLast(new FastImClientHandler());
                    }
                });
        logger.info("fastim client started");
        return bootstrap;
    }

    private String gateUrl;

    private Integer gatePort;

    /**
     * 客户端重连
     */
    public void connect(String gateUrl, Integer gatePort, Integer retryTimes) {
        if(gatePort == null || gateUrl == null){
            throw new RuntimeException("url or port is null");
        }
        this.gateUrl = gateUrl;
        this.gatePort = gatePort;
        ChannelFuture channelFuture = bootstrap.connect(gateUrl, gatePort).addListener(future -> {
            if (future.isSuccess()) {
                logger.info("连接成功!");
                /*
                 * 考虑到如果有许多好友和群，如果一次性拉取会有非常大的流量，可以采取延迟拉取和按需拉取的策略，还可以的话可以采用分页拉取群消息
                 * TODO 拉取以下信息
                 * 1）好友列表；
                 * 2）好友个人信息；   延迟拉取：打开个人窗口信息才拉取
                 * 3）群组列表；
                 * 4）群成员列表；    延迟拉取：打开某个群信息才拉取
                 * 5）群成员个人信息； 延迟拉取：打开个人窗口信息才拉取
                 * 6）离线消息。
                 */
            } else if (retryTimes == 0) {
                logger.info("重试次数已用完，放弃连接！");
            } else {
                int order = (connectRetry - retryTimes) + 1;
                logger.info("客户端第{}次重新ing", order);
                /*
                 * 当网络异常恢复后，大量客户端可能会同时发起TCP重连及进行应用层请求，可能会造成服务端过载、网络带宽耗尽等问题
                 * 所以增加指数退让机制
                 */
                int delay = 1 << order;
                // 定时到点执行
                bootstrap.config().group().schedule(() -> connect(gateUrl, gatePort, retryTimes - 1),
                        delay, TimeUnit.SECONDS);
            }
        });
        // TODO 连接失败channel如何处理
        channel = (NioSocketChannel) channelFuture.channel();
    }

    /**
     * 发送消息
     *
     * @param msg 消息体
     */
    public void send(FastImMsg msg, MsgRequest request) {
        if (channel == null) {
            logger.error("[channel][未初始化]");
            connect(gateUrl, gatePort,  1);
        }
        if (!channel.isActive() || !channel.isWritable()) {
            logger.error("[send][连接未激活][连接不可写]");
            channel.close().addListener(future -> {
                if (future.isSuccess()) {
                    connect(gateUrl, gatePort,  1);
                }
            });
        } else {
            // 发送消息
            int sequenceId = SequenceIdService.getInstance().generateSequenceId();
            msg.setSequenceId(sequenceId);
            channel.writeAndFlush(msg).addListener(future -> {
                // 增加定时器, 多久没有ack的就重复发送
                if (future.isSuccess()) {
                    Timeout timeout = ackTimer.newTimeout(new MsgAckTimerTask(channel, sendDuration, sendRetry, msg), sendDuration, TimeUnit.MILLISECONDS);
                    MsgManager.ACK_MSG_TIMEOUT_LIST.put(sequenceId, timeout);
                    MsgManager.MSG_REQUEST_LIST.put(sequenceId, request);
                }
            });
        }
    }

    public void destroy() {
        channel.close();
        ackTimer.stop();
        if (workerGroup != null && !workerGroup.isShutdown()) {
            workerGroup.shutdownGracefully();
        }
    }

    private Integer connectRetry;
    private Integer sendDuration;
    private Integer sendRetry;

    public void setConnectRetry(Integer connectRetry) {
        this.connectRetry = connectRetry;
    }

    public void setSendDuration(Integer sendDuration) {
        this.sendDuration = sendDuration;
    }
    public void setSendRetry(Integer sendRetry) {
        this.sendRetry = sendRetry;
    }
}
